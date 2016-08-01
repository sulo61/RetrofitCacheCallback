package com.mikesu.retrofitcachecallbackproject.model;

/**
 * Created by MikeSu on 01/08/16.
 * www.michalsulek.pl
 */
public class RandomNumber {

  private String type;
  private long length;
  private int[] data;
  private boolean success;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public long getLength() {
    return length;
  }

  public void setLength(long lenght) {
    this.length = lenght;
  }

  public int[] getData() {
    return data;
  }

  public void setData(int[] data) {
    this.data = data;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }
}
