package burlakov.learnthis.presenters.dialogs;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Objects;

import burlakov.learnthis.R;
import burlakov.learnthis.contracts.LogIn;

/**
 * Диалог загрузки аватара пользователя
 */
public class EditImageDialog extends DialogFragment implements View.OnClickListener {
    private FirebaseAuth auth;
    private ImageView avatar;
    private Button setAvatar;
    private String avatarImage;
    static final int GALLERY_REQUEST = 1;
    private StorageReference storageReference;
    private Uri imageUri;
    private StorageTask<UploadTask.TaskSnapshot> task;
    private int state = 0;
    FirebaseUser currentUser;
    String role;
    String nameInfo;
    LogIn.View view;

    public EditImageDialog(LogIn.View view) {
        this.view = view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        storageReference = FirebaseStorage.getInstance().getReference("images");
        builder.setView(inflater.inflate(R.layout.dialog_avatar, null))
                .setPositiveButton(R.string.edit, (dialog, id) -> {

                    if (task != null && task.isInProgress()) {
                        view.showMessage(getResources().getString(R.string.upload_on_process), false);
                    } else {
                        uploadImage();
                        updateAvatar();
                    }
                    EditImageDialog.this.getDialog().cancel();
                })
                .setNegativeButton(R.string.back, (DialogInterface.OnClickListener) (dialog, id) -> {
                    EditImageDialog.this.getDialog().cancel();
                });
        return builder.create();
    }

    Context tgetContext() {
        return getContext();
    }

    @Override
    public void onStart() {
        super.onStart();
        avatar = getDialog().findViewById(R.id.avatar);
        setAvatar = getDialog().findViewById(R.id.setAvatar);
        setAvatar.setOnClickListener(this);
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        String[] strings = Objects.requireNonNull(currentUser.getDisplayName()).split(" ");
        role = strings[2] + "s";
        nameInfo = strings[0] + " " + strings[1];
        if (state == 0) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Users").child(role).child(auth.getUid()).child("image");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    avatarImage = dataSnapshot.getValue(String.class);
                    if (state == 0) {
                        if (avatarImage.equals("def")) {
                            avatar.setImageResource(R.mipmap.ic_launcher);
                        } else {
                            Glide.with(tgetContext()).load(avatarImage).into(avatar);
                        }
                    }

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    avatar.setImageResource(R.mipmap.ic_launcher);

                }
            });
        }

    }

    /**
     * Обновляет аватар на новый
     */
    void updateAvatar() {
        NavigationView navigationView = getActivity().findViewById(R.id.nav_view);
        View hView = navigationView.getHeaderView(0);
        TextView name = hView.findViewById(R.id.name);
        ImageView imageView = hView.findViewById(R.id.image);
        name.setText(nameInfo);
        Glide.with(this).load(avatarImage).into(imageView);

    }

    @Override
    public void onClick(View v) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        if (imageReturnedIntent != null) {
            imageUri = imageReturnedIntent.getData();
            avatarImage = imageUri.toString();
            state = 1;
            avatar.setImageURI(imageUri);
        } else {
            state = 1;
            EditImageDialog.this.getDialog().cancel();
        }

    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    /**
     * Загрузка фото в бд
     */
    private void uploadImage() {
        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage(getResources().getString(R.string.upload_on));
        pd.show();

        if (imageUri != null) {
            final StorageReference fileReferense = storageReference.child(System.currentTimeMillis()
                    + "." + getFileExtension(imageUri));
            task = fileReferense.putFile(imageUri);
            task.continueWithTask((Continuation<UploadTask.TaskSnapshot, Task<Uri>>) task -> {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return fileReferense.getDownloadUrl();
            }).addOnCompleteListener((OnCompleteListener<Uri>) task -> {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    String mUri = downloadUri.toString();

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("Users").child(role).child(auth.getUid());
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("image", mUri);
                    myRef.updateChildren(hashMap);

                    pd.dismiss();
                } else {
                    view.showMessage(task.getException().getMessage(), false);
                    pd.dismiss();
                }
            }).addOnFailureListener((OnFailureListener) e -> {
                view.showMessage(task.getException().getMessage(), false);
                pd.dismiss();
            });
        } else {
            pd.dismiss();
        }
    }

}