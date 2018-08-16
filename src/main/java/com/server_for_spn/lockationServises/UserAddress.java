package com.server_for_spn.lockationServises;

import java.util.HashMap;
import java.util.Locale;

/**
 * Created by Victor on 16.08.2018.
 */
public class UserAddress {
    private Locale mLocale;
    private String mFeatureName;
    private HashMap<Integer, String> mAddressLines;
    private int mMaxAddressLineIndex = -1;
    private String mAdminArea;
    private String mSubAdminArea;
    private String mLocality;
    private String mSubLocality;
    private String mThoroughfare;
    private String mSubThoroughfare;
    private String mPremises;
    private String mPostalCode;
    private String mCountryCode;
    private String mCountryName;
    private double mLatitude;
    private double mLongitude;
    private boolean mHasLatitude = false;
    private boolean mHasLongitude = false;
    private String mPhone;
    private String mUrl;

    public Locale getmLocale() {
        return mLocale;
    }

    public void setmLocale(Locale mLocale) {
        this.mLocale = mLocale;
    }

    public String getmFeatureName() {
        return mFeatureName;
    }

    public void setmFeatureName(String mFeatureName) {
        this.mFeatureName = mFeatureName;
    }

    public HashMap<Integer, String> getmAddressLines() {
        return mAddressLines;
    }

    public void setmAddressLines(HashMap<Integer, String> mAddressLines) {
        this.mAddressLines = mAddressLines;
    }

    public int getmMaxAddressLineIndex() {
        return mMaxAddressLineIndex;
    }

    public void setmMaxAddressLineIndex(int mMaxAddressLineIndex) {
        this.mMaxAddressLineIndex = mMaxAddressLineIndex;
    }

    public String getmAdminArea() {
        return mAdminArea;
    }

    public void setmAdminArea(String mAdminArea) {
        this.mAdminArea = mAdminArea;
    }

    public String getmSubAdminArea() {
        return mSubAdminArea;
    }

    public void setmSubAdminArea(String mSubAdminArea) {
        this.mSubAdminArea = mSubAdminArea;
    }

    public String getmLocality() {
        return mLocality;
    }

    public void setmLocality(String mLocality) {
        this.mLocality = mLocality;
    }

    public String getmSubLocality() {
        return mSubLocality;
    }

    public void setmSubLocality(String mSubLocality) {
        this.mSubLocality = mSubLocality;
    }

    public String getmThoroughfare() {
        return mThoroughfare;
    }

    public void setmThoroughfare(String mThoroughfare) {
        this.mThoroughfare = mThoroughfare;
    }

    public String getmSubThoroughfare() {
        return mSubThoroughfare;
    }

    public void setmSubThoroughfare(String mSubThoroughfare) {
        this.mSubThoroughfare = mSubThoroughfare;
    }

    public String getmPremises() {
        return mPremises;
    }

    public void setmPremises(String mPremises) {
        this.mPremises = mPremises;
    }

    public String getmPostalCode() {
        return mPostalCode;
    }

    public void setmPostalCode(String mPostalCode) {
        this.mPostalCode = mPostalCode;
    }

    public String getmCountryCode() {
        return mCountryCode;
    }

    public void setmCountryCode(String mCountryCode) {
        this.mCountryCode = mCountryCode;
    }

    public String getmCountryName() {
        return mCountryName;
    }

    public void setmCountryName(String mCountryName) {
        this.mCountryName = mCountryName;
    }

    public double getmLatitude() {
        return mLatitude;
    }

    public void setmLatitude(double mLatitude) {
        this.mLatitude = mLatitude;
    }

    public double getmLongitude() {
        return mLongitude;
    }

    public void setmLongitude(double mLongitude) {
        this.mLongitude = mLongitude;
    }

    public boolean ismHasLatitude() {
        return mHasLatitude;
    }

    public void setmHasLatitude(boolean mHasLatitude) {
        this.mHasLatitude = mHasLatitude;
    }

    public boolean ismHasLongitude() {
        return mHasLongitude;
    }

    public void setmHasLongitude(boolean mHasLongitude) {
        this.mHasLongitude = mHasLongitude;
    }

    public String getmPhone() {
        return mPhone;
    }

    public void setmPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    public String getmUrl() {
        return mUrl;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    @Override
    public String toString() {
        return "UserAddress{" +
                "\nmLocale=" + mLocale +
                "\nmFeatureName='" + mFeatureName + '\'' +
                "\nmAddressLines=" + mAddressLines +
                "\nmMaxAddressLineIndex=" + mMaxAddressLineIndex +
                "\nmAdminArea='" + mAdminArea + '\'' +
                "\nmSubAdminArea='" + mSubAdminArea + '\'' +
                "\nmLocality='" + mLocality + '\'' +
                "\nmSubLocality='" + mSubLocality + '\'' +
                "\nmThoroughfare='" + mThoroughfare + '\'' +
                "\nmSubThoroughfare='" + mSubThoroughfare + '\'' +
                "\nmPremises='" + mPremises + '\'' +
                "\nmPostalCode='" + mPostalCode + '\'' +
                "\nmCountryCode='" + mCountryCode + '\'' +
                "\nmCountryName='" + mCountryName + '\'' +
                "\nmLatitude=" + mLatitude +
                "\nmLongitude=" + mLongitude +
                "\nmHasLatitude=" + mHasLatitude +
                "\nmHasLongitude=" + mHasLongitude +
                "\nmPhone='" + mPhone + '\'' +
                "\nmUrl='" + mUrl + '\'' +
                '}';
    }
}
