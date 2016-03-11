/*******************************************************************************
 * Copyright 2014 Sergey Tarasevich
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

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 */
public class SimpleImageActivity extends FragmentActivity {

	public int 	BACK_BUTTON_INDEX = -1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.gc();  //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>GARBAGE COLLECTION !!
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
				.build();
		ImageLoader.getInstance().init(config);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Garbage Collector in SimpleImageActivity<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		int frIndex = getIntent().getIntExtra(Constants.Extra.FRAGMENT_INDEX, 0);
		Fragment fr;
		String tag;
		int titleRes;
		switch (frIndex) {
			default:
			case ImageListFragment.INDEX:

				tag = ImageListFragment.class.getSimpleName();
				fr = getSupportFragmentManager().findFragmentByTag(tag);
				if (fr == null) {
					fr = new ImageListFragment();
				}
				//titleRes = R.string.ac_name_image_list;
				break;
			case ImageListFragmentFriends.INDEX:
				BACK_BUTTON_INDEX=ImageListFragmentFriends.INDEX;
				tag = ImageListFragmentFriends.class.getSimpleName();
				fr = getSupportFragmentManager().findFragmentByTag(tag);
				if (fr == null) {
					fr = new ImageListFragmentFriends();
				}
				//titleRes = R.string.ac_name_image_list;
				break;
			case ImageListFragmentPosts.INDEX:
				tag = ImageListFragmentPosts.class.getSimpleName();
				fr = getSupportFragmentManager().findFragmentByTag(tag);
				if (fr == null) {
					fr = new ImageListFragmentPosts();
				}
				//titleRes = R.string.ac_name_image_list;
				break;
			case ImageListFragmentFriendRequests.INDEX:
				BACK_BUTTON_INDEX=ImageListFragmentFriendRequests.INDEX;
				tag = ImageListFragmentFriendRequests.class.getSimpleName();
				fr = getSupportFragmentManager().findFragmentByTag(tag);
				if (fr == null) {
					fr = new ImageListFragmentFriendRequests();
				}
				//titleRes = R.string.ac_name_image_list;
				break;
			case ImageListFragmentReports.INDEX:
				tag = ImageListFragmentReports.class.getSimpleName();
				fr = getSupportFragmentManager().findFragmentByTag(tag);
				if (fr == null) {
					fr = new ImageListFragmentReports();
				}
				//titleRes = R.string.ac_name_image_list;
				break;

		}



		//setTitle(titleRes);
		getSupportFragmentManager().beginTransaction().replace(android.R.id.content, fr, tag).commit();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Runtime.getRuntime().gc();
	}

	@Override
	public void onBackPressed() {
		if(BACK_BUTTON_INDEX == -1){
			Intent intent = new Intent(this, PublicSigninSignUp.class);
			startActivity(intent);
			overridePendingTransition(0, 0);
			finish();}
		else {
			Intent intent = new Intent(this, SimpleImageActivity.class);
			intent.putExtra(Constants.Extra.FRAGMENT_INDEX, ImageListFragmentPosts.INDEX);
			startActivity(intent);
			overridePendingTransition(0, 0);
			finish();
		}
	}

	/*	@Override
	public void onBackPressed() {
		Intent intent = new Intent(this, SimpleImageActivity.class);
		intent.putExtra(Constants.Extra.FRAGMENT_INDEX, ImageListFragmentPosts.INDEX);
		startActivity(intent);
	}*/

}