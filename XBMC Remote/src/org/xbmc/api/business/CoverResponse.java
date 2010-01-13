package org.xbmc.api.business;

import org.xbmc.android.remote.presentation.widget.IItemView;
import org.xbmc.api.object.ICoverArt;
import org.xbmc.api.type.ThumbSize;

import android.graphics.Bitmap;

public class CoverResponse extends DataResponse<Bitmap> {
	
	private final IItemView mView;
	private final IManager mManager;
	
	private boolean mIsLoading = false;
	private ICoverArt mMostRecentCover = null;
	
	public CoverResponse(IItemView view, IManager manager) {
		mView = view;
		mManager = manager;
	}
	
	public synchronized void load(ICoverArt cover) {
		if (mIsLoading) {
			mMostRecentCover = cover;
		} else {
			mIsLoading = true;
			mMostRecentCover = null;
			mManager.getCover(this, cover, ThumbSize.SMALL);
		}
	}
	
	public synchronized void run() {
		if (mMostRecentCover == null) {
			mView.setCover(value);
			mIsLoading = false;
		} else {
			mManager.getCover(this, mMostRecentCover, ThumbSize.SMALL);
			mMostRecentCover = null;
		}
	}
}