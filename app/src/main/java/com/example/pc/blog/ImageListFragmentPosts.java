/*******************************************************************************
 * Copyright 2011-2014 Sergey Tarasevich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.example.pc.blog;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 */
public class ImageListFragmentPosts extends AbsListViewBaseFragment implements AsyncResponse {

	public static final int INDEX = 2;
	static ArrayList<PostItem> PostItems;
	MyPHPHandler phpHandler,myPHPHandlerNotification;
	public static String UserEmail,UserID,CurrentNotificationNumber;
	SharedPreferences sharedPref;
	public static Activity activity;
	TextView NotificationText;
	ImageButton NotificationsButton,AddButton,FriendssButton;
	SwipeRefreshLayout swipeView;

	@Override

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		ActionBar Act=getActivity().getActionBar();
		Act.hide();
		activity=getActivity();

		PostItems=new ArrayList<PostItem>();

		sharedPref = getActivity().getSharedPreferences("LoginInfo", Context.MODE_PRIVATE);
		UserEmail= sharedPref.getString("Email", "");
		UserID= sharedPref.getString("ID", "");
		CurrentNotificationNumber=sharedPref.getString("CurrentNotificationNumber", "");
		System.out.println("||||||||||||||||||||||||||||||||||| IN POSTS USER ID   :    " + UserID);

		phpHandler = new MyPHPHandler(getActivity(),"print_posts");
		phpHandler.execute(UserID);
		phpHandler.delegate = this;



		View rootView = inflater.inflate(R.layout.activity_public_user_home, container, false);

		listView = (ListView) rootView.findViewById(R.id.ThePostsListView);

		NotificationText=(TextView)rootView.findViewById(R.id.NotificationText);
		NotificationsButton=(ImageButton)rootView.findViewById(R.id.NotificationsButton);
		AddButton=(ImageButton)rootView.findViewById(R.id.AddButton);
		FriendssButton=(ImageButton)rootView.findViewById(R.id.FriendsButton);

		swipeView=(SwipeRefreshLayout)rootView.findViewById(R.id.swipe_view);
		NotificationText.setVisibility(View.GONE);
		swipeView.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				swipeView.setRefreshing(true);
				swipeView.postDelayed(new Runnable() {

					@Override
					public void run() {
						//  swipeView.setRefreshing(false);
						Intent intent = new Intent(getActivity(), SimpleImageActivity.class);
						intent.putExtra(Constants.Extra.FRAGMENT_INDEX, ImageListFragmentPosts.INDEX);
						getActivity().startActivity(intent);
						getActivity().overridePendingTransition(0, 0);
						getActivity().finish();
					}
				}, 500);
			}
		});
		swipeView.setColorSchemeColors(Color.GRAY, Color.GREEN, Color.BLUE,Color.RED, Color.CYAN);
		swipeView.setDistanceToTriggerSync(10);// in dips
		swipeView.setSize(SwipeRefreshLayout.DEFAULT);// LARGE also can be used

		NotificationsButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new MyPHPHandler(getActivity(), "db_update_friend_request_status").execute(UserID);
				Intent intent = new Intent(getActivity(), SimpleImageActivity.class);
				intent.putExtra(Constants.Extra.FRAGMENT_INDEX, ImageListFragmentFriendRequests.INDEX);
				ImageLoader.getInstance().clearMemoryCache();
				ImageLoader.getInstance().clearDiskCache();
				startActivity(intent);
				getActivity().overridePendingTransition(0, 0);
				getActivity().finish();
			}
		});

		AddButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), PublicAddPost.class);
				startActivity(i);
				getActivity().overridePendingTransition(0, 0);
				getActivity().finish();
			}
		});

		FriendssButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), SimpleImageActivity.class);
				intent.putExtra(Constants.Extra.FRAGMENT_INDEX, ImageListFragmentFriends.INDEX);
				ImageLoader.getInstance().clearMemoryCache();
				ImageLoader.getInstance().clearDiskCache();
				startActivity(intent);
				getActivity().overridePendingTransition(0, 0);
				getActivity().finish();
			}
		});

		return rootView;
	}


	@Override
	public void onDestroy() {
		super.onDestroy();
		AnimateFirstDisplayListener.displayedImages.clear();
	}

	private static class ImageAdapter extends BaseAdapter {

		private static final String[] IMAGE_URLS = Constants.IMAGES;
		private LayoutInflater inflater;
		private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

		private DisplayImageOptions options;

		ImageAdapter(Context context) {
			inflater = LayoutInflater.from(context);

			options = new DisplayImageOptions.Builder()
					.showImageOnLoading(R.drawable.ic_stub)
					.showImageForEmptyUri(R.drawable.ic_empty)
					.showImageOnFail(R.drawable.ic_error)
					.cacheInMemory(true)
					.cacheOnDisk(true)
					.considerExifParams(true)
					.displayer(new RoundedBitmapDisplayer(20)).build();
		}

		@Override
		public int getCount() {
			//return IMAGE_URLS.length;
			return PostItems.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			View view = convertView;
			final ViewHolder holder;
			if (convertView == null) {
				view = inflater.inflate(R.layout.custom_public_post_row, parent, false);
				holder = new ViewHolder();
				holder.UserName = (TextView) view.findViewById(R.id.TextUserName);
				holder.PostTime = (TextView) view.findViewById(R.id.TextPostTime);
				holder.PostText = (TextView) view.findViewById(R.id.TextPost);
				holder.UserImage = (ImageView) view.findViewById(R.id.UserImage);
				holder.PostImage = (ImageView) view.findViewById(R.id.PostImage);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
				//return convertView; //added by me !!
			}
			//Date Parts
			String[] DateParts = PostItems.get(position).getPostDate().split("/");
			//Time Parts
			String[] TimeParts = PostItems.get(position).getPostTime().split(":");
			Time_Ago time_ago=new Time_Ago();
			//Day/Month/Year .. Hour:Minute


			String URL= MyPHPHandler.Online_URL+"uploadedimages/";


			holder.UserName.setText(PostItems.get(position).getUserName());
			holder.PostTime.setText(time_ago.Get_Time(DateParts[2],DateParts[1],DateParts[0],TimeParts[0],TimeParts[1]) + " ago");
			holder.PostText.setText(PostItems.get(position).getPostText());
			String UserImage=URL+PostItems.get(position).getUserImage();
			String PostImage=URL+PostItems.get(position).getPostImage();
			//"http://10.0.3.2/Blog/uploadedimages/"+

			System.gc();  //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>GARBAGE COLLECTION !!
			if (holder.UserImage.getTag() == null ||!holder.UserImage.getTag().equals(UserImage)){
				ImageLoader.getInstance().displayImage(UserImage, holder.UserImage, options, animateFirstListener);
			}

			if(PostItems.get(position).getPostImage().equals("none"))holder.PostImage.setVisibility(View.GONE);
			else {
				holder.PostImage.setVisibility(View.VISIBLE);
			if (holder.PostImage.getTag() == null ||!holder.PostImage.getTag().equals(PostImage)) {
				ImageLoader.getInstance().displayImage(PostImage, holder.PostImage, options, animateFirstListener);
			}
			}
			//ImageLoader.getInstance().displayImage(IMAGE_URLS[position], holder.image, options, animateFirstListener);

			return view;
		}


	}

	static class ViewHolder {
		TextView UserName;
		TextView PostTime;
		TextView PostText;
		ImageView UserImage;
		ImageView PostImage;
	}

	private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}



	//////////////////////////////////////////////PHP Response///////////////////////////////////////////
	public void processFinish(String output){
		//this you will received result fired from async class of onPostExecute(result) method.
		PostItems.clear();
		System.out.println("|||||||||||||||||||||||||||||||||||||||| Result in CLASS Posts : " + output);
		String[] parts = output.split("\\#");

		//part[0] --> not seen requests
		if(Integer.parseInt(parts[0])>0){
			NotificationText.setVisibility(View.VISIBLE);
			NotificationText.setText(parts[0]);
		}

		for (int i=1;i<parts.length-1;i+=6){
			PostItem temp=new PostItem(parts[i],parts[i+1],parts[i+2],parts[i+3],parts[i+4],parts[i+5]);
			PostItems.add(temp);
			System.out.println("Name : "+parts[i]+"\nID : "+parts[i+1]+"\nImage : "+parts[i+2]);
			System.out.println("_________________________________________________________");
		}
		((ListView) listView).setAdapter(new ImageAdapter(getActivity()));

	}



	//////////////////////////////////////Friend Item/////////////////////////////////////////////////


	public class PostItem {

		private String UserName,PostText,PostDate,PostTime,UserImage,PostImage;

		public PostItem(String userName, String postText, String postDate, String postTime, String userImage, String postImage) {
			UserName = userName;
			PostText = postText;
			PostDate = postDate;
			PostTime = postTime;
			UserImage = userImage;
			PostImage = postImage;
		}

		public String getUserName() {
			return UserName;
		}

		public String getPostText() {
			return PostText;
		}

		public String getPostDate() {
			return PostDate;
		}

		public String getPostTime() {
			return PostTime;
		}

		public String getUserImage() {
			return UserImage;
		}

		public String getPostImage() {
			return PostImage;
		}
	}



}