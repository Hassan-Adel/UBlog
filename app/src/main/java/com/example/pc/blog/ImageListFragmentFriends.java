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
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
public class ImageListFragmentFriends extends AbsListViewBaseFragment implements AsyncResponse {

	public static final int INDEX = 1;
	static ArrayList<FriendItem> FriendItems;
	MyPHPHandler phpHandler;
	public static String UserEmail,UserID;
	SharedPreferences sharedPref;
	public static Activity activity;
	ImageButton AddFriendsButton;

	@Override

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		ActionBar Act=getActivity().getActionBar();
		Act.hide();

		activity=getActivity();

		FriendItems=new ArrayList<FriendItem>();

		sharedPref = getActivity().getSharedPreferences("LoginInfo", Context.MODE_PRIVATE);
		UserEmail= sharedPref.getString("Email","");
		UserID= sharedPref.getString("ID", "");

		phpHandler=new MyPHPHandler(getActivity(),"print_friends");
		phpHandler.execute(UserEmail);
		phpHandler.delegate = this;
		View rootView = inflater.inflate(R.layout.activity_public_friends, container, false);
		listView = (ListView) rootView.findViewById(R.id.TheFriendsListView);
		AddFriendsButton=(ImageButton)rootView.findViewById(R.id.AdUserImageButton);
		AddFriendsButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), PublicAddFriend.class);
				startActivity(intent);
				getActivity().overridePendingTransition(0, 0);
				getActivity().finish();
			}
		});
		/*
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				startImagePagerActivity(position);
			}
		});
		*/
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
			return FriendItems.size();
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
				view = inflater.inflate(R.layout.custom_public_friend_row, parent, false);
				holder = new ViewHolder();
				holder.FriendName = (TextView) view.findViewById(R.id.TextFriendName);
				holder.FriendID = (TextView) view.findViewById(R.id.TextFriendId);
				holder.image = (ImageView) view.findViewById(R.id.FriendImage);
				holder.ReportFriendButton=(Button)view.findViewById(R.id.ReportFriendButton);
				holder.DeleteFriendButton=(Button)view.findViewById(R.id.DeleteFriendButton);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
				//return convertView; //added by me !!
			}
			System.out.println("||||||||||| Size : " + FriendItems.size());
			//holder.FriendName.setText("Item " + (position + 1));
			holder.FriendName.setText(FriendItems.get(position).getFriendName());
			holder.FriendID.setText(FriendItems.get(position).getFriendID());
			String Image= MyPHPHandler.Online_URL+"uploadedimages/"+FriendItems.get(position).FriendImage;
			//"http://10.0.3.2/Blog/uploadedimages/"+

			System.gc();  //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>GARBAGE COLLECTION !!

			if (holder.image.getTag() == null ||!holder.image.getTag().equals(Image)) {
				ImageLoader.getInstance().displayImage(Image, holder.image, options, animateFirstListener);
			}
			//ImageLoader.getInstance().displayImage(IMAGE_URLS[position], holder.image, options, animateFirstListener);
			holder.ReportFriendButton.setOnClickListener(
					new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							ReportDailog(holder.FriendID.getText().toString());
						}
					}
			);
			holder.DeleteFriendButton.setOnClickListener(
					new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							DeleteDailog(holder.FriendID.getText().toString(), holder.FriendName.getText().toString());
						}
					}
			);

			return view;
		}






	}

	static class ViewHolder {
		TextView FriendName;
		TextView FriendID;
		Button ReportFriendButton;
		Button DeleteFriendButton;
		ImageView image;
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

		System.out.println("|||||||||||||||||||||||||||||||||||||||| Result in CLASS : " + output);
		String[] parts = output.split("\\#");
		for (int i=0;i<parts.length-1;i+=3) {
			if(!UserID.equals(parts[i+1])) {
			FriendItem temp = new FriendItem(parts[i], parts[i + 1], parts[i + 2]);
			FriendItems.add(temp);
			System.out.println("Name : " + parts[i] + "\nID : " + parts[i + 1] + "\nImage : " + parts[i + 2]);
			System.out.println("_________________________________________________________");
		}

		}
		((ListView) listView).setAdapter(new ImageAdapter(getActivity()));

	}

	//////////////////////////////////////Friend Item/////////////////////////////////////////////////


	public class FriendItem {

		private String FriendName;
		private String FriendID;
		private String FriendImage;

		public String getFriendName() {
			return FriendName;
		}

		public String getFriendID() {
			return FriendID;
		}

		public String getFriendImage() {
			return FriendImage;
		}

		public FriendItem(String friendName, String friendID, String friendImage) {

			FriendName = friendName;
			FriendID = friendID;
			FriendImage = friendImage;
		}
	}


	////////////////////////////////////////////////////Delete Dialog///////////////////////////////////////////////
	private static void DeleteDailog(final String Friend_ID,final String FriendName) {
		System.out.println("id delete " + Friend_ID);
		final Dialog MyDialog = new Dialog(activity);
		LayoutInflater layoutInflater = LayoutInflater.from(activity);
		View dialog = layoutInflater.inflate(R.layout.activity_custom_dialog_delete_friend, null);

		MyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        MyDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		MyDialog.setContentView(dialog);

		ImageButton delete = (ImageButton) dialog.findViewById(R.id.DeleteFriendButton);
		TextView username = (TextView) dialog.findViewById(R.id.TextDeleteFriendDialog);
		username.setText(username.getText() + FriendName);
		delete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				MyPHPHandler myPHPHandler = new MyPHPHandler(activity, "delete_friend");
				myPHPHandler.execute(UserID, Friend_ID);
				MyDialog.cancel();
				MyDialog.dismiss();

			}
		});

		MyDialog.show();
	}

	/////////////////////////////////////////////////////Report Dialog///////////////////////////////////////////////
	private static void ReportDailog(final String Friend_ID) {//holder.FriendID.getText().toString(), holder.FriendName.getText().toString()
		System.out.println("id report " + Friend_ID);
		final String[] report_reason = new String[1];
		final Dialog MyDialog = new Dialog(activity);
		LayoutInflater layoutInflater = LayoutInflater.from(activity);
		View dialog = layoutInflater.inflate(R.layout.activity_custom_dialog_report_friend, null);
		MyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		MyDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		MyDialog.setContentView(dialog);

		final ImageButton report = (ImageButton) dialog.findViewById(R.id.ReportFriendButton);

		// This will get the radiogroup
		RadioGroup rGroup = (RadioGroup)dialog.findViewById(R.id.radioGroup);
// This will get the radiobutton in the radiogroup that is checked
		RadioButton checkedRadioButton = (RadioButton)rGroup.findViewById(rGroup.getCheckedRadioButtonId());

		// This overrides the radiogroup onCheckListener
		rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup rGroup, int checkedId) {
				// This will get the radiobutton that has changed in its check state
				RadioButton checkedRadioButton = (RadioButton) rGroup.findViewById(checkedId);
				// This puts the value (true/false) into the variable
				boolean isChecked = checkedRadioButton.isChecked();
				// If the radiobutton that has changed in check state is now checked...
				if (isChecked) {
					// Changes the textview's text to "Checked: example radiobutton text"
					if (checkedRadioButton.getId() == R.id.radioButton1)
						report_reason[0] = "This is a fake acount";
					else if (checkedRadioButton.getId() == R.id.radioButton2)
						report_reason[0] = "Post impropriate content";
					else if (checkedRadioButton.getId() == R.id.radioButton3)
						report_reason[0] = "Post wrong data";
				}

				report.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						MyPHPHandler myPHPHandler= new MyPHPHandler(activity, "report_friend");
						myPHPHandler.execute(UserID, Friend_ID, report_reason[0]);
						MyDialog.cancel();
						MyDialog.dismiss();

					}
				});

			}
		});


		MyDialog.show();
	}




}