package luongduongquan.com.myblogapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

	private RecyclerView mListBlog;

	private DatabaseReference mDatabase;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mListBlog = findViewById(R.id.list_blog);

		mListBlog.setHasFixedSize(true);
		mListBlog.setLayoutManager(new LinearLayoutManager(this));

		mDatabase = FirebaseDatabase.getInstance().getReference().child("Blog");


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.main_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()){
			case R.id.action_add:
				startActivity(new Intent(MainActivity.this, PostActivity.class));
				break;
			case R.id.action_settings:
				break;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onStart() {
		super.onStart();

		FirebaseRecyclerAdapter<Blog, BlogViewHolder> adapter  = new FirebaseRecyclerAdapter<Blog, BlogViewHolder>(
				Blog.class,
				R.layout.item_blog,
				BlogViewHolder.class,
				mDatabase
		) {
			@Override
			protected void populateViewHolder(BlogViewHolder viewHolder, Blog model, int position) {
				Log.d("QUANTEST", "title = " + model.getTitle()
						+ " --- Descript = " + model.getdescript()
						+ " --- Image = " + model.getImage());
				viewHolder.setTitle(model.getTitle());
				viewHolder.setDescipt(model.getdescript());
				viewHolder.setImage(model.getImage());

			}

			@Override
			public BlogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
				BlogViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);

				viewHolder.setCustomOnItemClick(new BlogViewHolder.CustomOnItemClick() {
					@Override
					public void OnItemClickListenerQuan(int position) {
						Toast.makeText(MainActivity.this, "Click item = " + position, Toast.LENGTH_SHORT).show();
					}
				});



				return viewHolder;
			}
		};



//		FirebaseRecyclerOptions<Blog> options =
//				new FirebaseRecyclerOptions.Builder<Blog>()
//						.setQuery(mDatabase, Blog.class)
//						.build();
//
//		FirebaseRecyclerAdapter<Blog, BlogViewHolder> adapter = new FirebaseRecyclerAdapter<Blog, BlogViewHolder>(options) {
//			@NonNull
//			@Override
//			public BlogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//				View view = LayoutInflater.from(parent.getContext())
//						.inflate(R.layout.item_blog, parent, false);
//
//				return new BlogViewHolder(view);
//
//			}
//
//			@Override
//			protected void onBindViewHolder(@NonNull BlogViewHolder holder, int position, @NonNull Blog model) {
//
//			}
//		};

		mListBlog.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}

	public static class BlogViewHolder extends RecyclerView.ViewHolder {

		View mView;
		private CustomOnItemClick mCustomItemOnClick;

		public BlogViewHolder(View itemView) {
			super(itemView);

			mView = itemView;
			itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					mCustomItemOnClick.OnItemClickListenerQuan(getPosition());
				}
			});
		}

		public void setTitle(String title){
			TextView post_title = mView.findViewById(R.id.tvTitle_Main);
			post_title.setText(title);
		}

		public void setDescipt(String descipt){
			TextView post_descript = mView.findViewById(R.id.tvDescript_Main);
			post_descript.setText(descipt);
		}

		public void setImage(String Uri){
			ImageView post_img = mView.findViewById(R.id.imgPost_Main);
			if(!Uri.isEmpty()){
				Picasso.get().load(android.net.Uri.parse(Uri)).into(post_img);
			}

		}

		public interface CustomOnItemClick{
			void OnItemClickListenerQuan(int position);
		}

		public void setCustomOnItemClick(CustomOnItemClick customOnClick) {
			this.mCustomItemOnClick = customOnClick;
		}


	}
}
