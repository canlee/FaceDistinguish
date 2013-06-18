package com.invindible.facetime.service.implement;

import java.awt.Image;

import com.invindible.facetime.service.interfaces.FindFaceInterface;
import com.invindible.facetime.task.image.FindFaceTask;
import com.invindible.facetime.task.interfaces.Context;

public class FindFaceInterfaceImpl implements FindFaceInterface {

	private FindFaceTask findTask;
	private Context context;
	
	public FindFaceInterfaceImpl(Context context) {
		this.context = context;
		findTask = null;
	}
	
	@Override
	public void start() {
		if(findTask == null) {
			findTask = new FindFaceTask(context);
			findTask.start();
		}
	}

	@Override
	public void findFace(Image image) {
			if(findTask != null) {
				findTask.find(image);
			}
	}

	@Override
	public void stop() {
		if(findTask != null) {
			findTask.stopTask();
			findTask = null;
		}
	}

}
