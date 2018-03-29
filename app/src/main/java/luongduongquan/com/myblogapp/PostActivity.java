package luongduongquan.com.myblogapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class PostActivity extends AppCompatActivity {

	private ImageButton imgSelectImage;
	private EditText edtTitle;
	private EditText edtDescription;
	private Button btnSubmitPost;
	private Uri imageURI;

	private StorageReference mStorageReference;
	private ProgressDialog mProgressDialog;

	private static final int GALLERY_REQUEST = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post);

		imgSelectImage = findViewById(R.id.imgAddButton_Post);
		edtTitle = findViewById(R.id.edtTitle_Post);
		edtDescription = findViewById(R.id.edtDescription_Post);
		btnSubmitPost = findViewById(R.id.btnPost_Post);

		mProgressDialog = new ProgressDialog(this);
		mProgressDialog.setTitle("Upload post");
		mProgressDialog.setMessage("Please wait...");
		mProgressDialog.setCanceledOnTouchOutside(false);

		mStorageReference = FirebaseStorage.getInstance().getReference();



		imgSelectImage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intentToCamera = new Intent(Intent.ACTION_GET_CONTENT);
				intentToCamera.setType("image/*");
				startActivityForResult(intentToCamera, GALLERY_REQUEST);
			}
		});

		btnSubmitPost.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				startPosting();
			}
		});

	}

	private void startPosting() {
		mProgressDialog.show();
		String title = edtTitle.getText().toString().trim();
		String descrip = edtDescription.getText().toString().trim();
		if(title.isEmpty() || descrip.isEmpty() || imageURI == null){
			Toast.makeText(this, "Something is empty...", Toast.LENGTH_SHORT).show();
			mProgressDialog.dismiss();
		} else {
			StorageReference filePath = mStorageReference.child("Blog_Images").child(imageURI.getLastPathSegment());

			filePath.putFile(imageURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
				@Override
				public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


					Uri downloadURL = taskSnapshot.getDownloadUrl();


					mProgressDialog.dismiss();
				}
			});

			filePath.putFile(imageURI).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
				@Override
				public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {


				}
			});
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){
			imageURI = data.getData();

			imgSelectImage.setImageURI(imageURI);
		}
	}
}
