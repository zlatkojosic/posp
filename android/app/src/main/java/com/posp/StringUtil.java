package com.posp;

public class StringUtil{

 public static String join( List<String> list , String replacement  ) {
     StringBuilder b = new StringBuilder();
     for( String item: list ) {
         b.append( replacement ).append( item );
     }
     return b.toString().substring( replacement.length() );
 }
}