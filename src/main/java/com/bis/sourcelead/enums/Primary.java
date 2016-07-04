package com.bis.sourcelead.enums;

public enum Primary {

 Yes(1), NO(0);
 
 private final int value;
 private Primary(int value) {
     this.value = value;
 }

 public int getValue() {
     return value;
 }
 
}
