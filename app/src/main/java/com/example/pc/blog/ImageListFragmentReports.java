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
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
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
public class ImageListFragmentReports extends AbsListViewBaseFragment implements AsyncResponse {

	public static final int INDEX = 4;
	static ArrayList<ReportItem> ReportItems;
	MyPHPHandler phpHandler;
	public static String UserID;
	SharedPreferences sharedPref;
	public static Activity activity;
	TextView NotificationText;

	@Override

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		ActionBar Act=getActivity().getActionBar();
		Act.hide();
		activity=getActivity();

		ReportItems=new ArrayList<ReportItem>();

		sharedPref = getActivity().getSharedPreferences("LoginInfo", Context.MODE_PRIVATE);
		//UserID= sharedPref.getString("ID", "");
		UserID="8";
		System.out.println("USER ID   :    " + UserID);
		phpHandler = new MyPHPHandler(getActivity(),"print_all_reports");
		phpHandler.delegate = this;
		phpHandler.execute(UserID);
		View rootView = inflater.inflate(R.layout.activity_public_reported_users, container, false);
		listView = (ListView) rootView.findViewById(R.id.TheReportsListView);

		return rootView;
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		AnimateFirstDisplayListener.displayedImages.clear();
	}

	private static class ImageAdapter extends BaseAdapter {

		MyPHPHandler PHPHandler;
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
			return ReportItems.size();
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
				view = inflater.inflate(R.layout.custom_public_report_row, parent, false);
				holder = new ViewHolder();
				holder.ReporterName = (TextView) view.findViewById(R.id.TextReporterName);
				holder.ReporterID = (TextView) view.findViewById(R.id.TextReporterId);
				holder.TheReporterImage = (ImageView) view.findViewById(R.id.ReporterImage);
				holder.ReportedName = (TextView) view.findViewById(R.id.TextReportedName);
				holder.ReportedID = (TextView) view.findViewById(R.id.TextReportedId);
				holder.TheReportedImage = (ImageView) view.findViewById(R.id.ReportedImage);
				holder.ReportReason = (TextView) view.findViewById(R.id.TextReportReason);
				holder.DeleteUserButton = (Button) view.findViewById(R.id.RemoveUserAndReportButton);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
				//return convertView; //added by me !!
			}

			String URL= MyPHPHandler.Online_URL+"uploadedimages/";

			holder.ReporterName.setText(ReportItems.get(position).getUserName());
			holder.ReporterID.setText(ReportItems.get(position).getUserID());
			holder.ReportReason.setText(ReportItems.get(position).getReportReason());
			holder.ReportedName.setText(ReportItems.get(position).getReportedName());
			holder.ReportedID.setText(ReportItems.get(position).getReportedID());

			String ReporterImage=URL+ReportItems.get(position).getUserImage();
			String ReportedImage=URL+ReportItems.get(position).getReportedImage();
			//"http://10.0.3.2/Blog/uploadedimages/"+
			System.gc();  //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>GARBAGE COLLECTION !!
			if (holder.TheReporterImage.getTag() == null ||!holder.TheReporterImage.getTag().equals(ReporterImage)) {
				ImageLoader.getInstance().displayImage(ReporterImage, holder.TheReporterImage, options, animateFirstListener);
			}
			if (holder.TheReportedImage.getTag() == null ||!holder.TheReportedImage.getTag().equals(ReportedImage)) {
				ImageLoader.getInstance().displayImage(ReportedImage, holder.TheReportedImage, options, animateFirstListener);
			}
			//ImageLoader.getInstance().displayImage(IMAGE_URLS[position], holder.image, options, animateFirstListener);

			holder.DeleteUserButton.setOnClickListener(
					new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							PHPHandler = new MyPHPHandler(activity, "drop_user");
							PHPHandler.execute(ReportItems.get(position).getReportedID());
						}
					}
			);

			return view;
		}


	}

	static class ViewHolder {
		TextView ReporterName;
		TextView ReporterID;
		ImageView TheReporterImage;
		TextView ReportedName;
		TextView ReportedID;
		ImageView TheReportedImage;
		TextView ReportReason;
		Button DeleteUserButton;
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
		for (int i=0;i<parts.length-1
				;i+=7) {

			//(String userName, String userID, String userImage, String reportedName, String reportedID, String reportedImage, String reportReason)
			//Hassan#8#male.png#mezo#15#male.png#Post impropriate content#
			ReportItem temp = new ReportItem(parts[i], parts[i + 1], parts[i + 2], parts[i + 3], parts[i + 4], parts[i + 5], parts[i + 6]);
			ReportItems.add(temp);
			System.out.println("Name : " + parts[i] + "\nID : " + parts[i + 1] + "\nImage : " + parts[i + 2]);
			System.out.println("_________________________________________________________");
		}
		((ListView) listView).setAdapter(new ImageAdapter(getActivity()));

	}


	//////////////////////////////////////Report Item/////////////////////////////////////////////////


	public class ReportItem {

		private String UserName;
		private String UserID;
		private String UserImage;
		private String ReportedName;
		private String ReportedID;
		private String ReportedImage;
		private String ReportReason;

		public ReportItem(String userName, String userID, String userImage, String reportedName, String reportedID, String reportedImage, String reportReason) {
			UserName = userName;
			UserID = userID;
			UserImage = userImage;
			ReportedName = reportedName;
			ReportedID = reportedID;
			ReportedImage = reportedImage;
			ReportReason = reportReason;
		}

		public String getUserName() {
			return UserName;
		}

		public String getUserID() {
			return UserID;
		}

		public String getUserImage() {
			return UserImage;
		}

		public String getReportedName() {
			return ReportedName;
		}

		public String getReportedID() {
			return ReportedID;
		}

		public String getReportedImage() {
			return ReportedImage;
		}

		public String getReportReason() {
			return ReportReason;
		}
	}




}