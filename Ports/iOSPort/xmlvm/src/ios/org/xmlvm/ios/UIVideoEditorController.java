package org.xmlvm.ios;
import java.util.*;

import org.xmlvm.XMLVMSkeletonOnly;
@XMLVMSkeletonOnly
public class UIVideoEditorController extends UINavigationController {

	/*
	 * Static methods
	 */

	/**
	 * + (BOOL)canEditVideoAtPath:(NSString *)videoPath ;
	 */
	public static boolean canEditVideoAtPath(String videoPath){
		throw new RuntimeException("Stub");
	}

	/*
	 * Constructors
	 */

	/** Default constructor */
	UIVideoEditorController() {}

	/*
	 * Properties
	 */

	/**
	 * @property(nonatomic,assign) id <UINavigationControllerDelegate, UIVideoEditorControllerDelegate> delegate;
	 */
	public UINavigationControllerDelegate getDelegate(){
		throw new RuntimeException("Stub");
	}

	/**
	 * @property(nonatomic,assign) id <UINavigationControllerDelegate, UIVideoEditorControllerDelegate> delegate;
	 */
	public void setDelegate(UINavigationControllerDelegate delegate){
		throw new RuntimeException("Stub");
	}

	/**
	 * @property(nonatomic, copy) NSString *videoPath;
	 */
	public String getVideoPath(){
		throw new RuntimeException("Stub");
	}

	/**
	 * @property(nonatomic, copy) NSString *videoPath;
	 */
	public void setVideoPath(String videoPath){
		throw new RuntimeException("Stub");
	}

	/**
	 * @property(nonatomic) NSTimeInterval videoMaximumDuration;
	 */
	public double getVideoMaximumDuration(){
		throw new RuntimeException("Stub");
	}

	/**
	 * @property(nonatomic) NSTimeInterval videoMaximumDuration;
	 */
	public void setVideoMaximumDuration(double videoMaximumDuration){
		throw new RuntimeException("Stub");
	}

	/**
	 * @property(nonatomic) UIImagePickerControllerQualityType videoQuality;
	 */
	public int getVideoQuality(){
		throw new RuntimeException("Stub");
	}

	/**
	 * @property(nonatomic) UIImagePickerControllerQualityType videoQuality;
	 */
	public void setVideoQuality(int videoQuality){
		throw new RuntimeException("Stub");
	}
}
