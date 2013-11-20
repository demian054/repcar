/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
 *
 * @author demian
 */
public abstract class functions {
    
    public static Object isNullOrEmpty(Object object, Object defaultValue) {
            if (object != null) {
                    return object;
            }
            return defaultValue;	
    }
    
    
    public static boolean isNullOrEmpty(String string) {
            return (string != null && !string.equalsIgnoreCase(""));	
    }
    
    
    public static String addWhithComma(String total, String toAdd){
            if (total.equalsIgnoreCase("")){
                return toAdd;
            }else{
                return total+","+toAdd;
            }
    }
    
                    
    static ArrayList<DateFormat> dateFormats = new ArrayList<DateFormat>();


    private static void defFormats(){
                   dateFormats.add(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"));
                   dateFormats.add(new SimpleDateFormat("dd/MM/yy hh:mm:ss"));
                   dateFormats.add(new SimpleDateFormat("dd/MM/yy")); 
                   dateFormats.add(new SimpleDateFormat("dd-MMM-yy hh.mm.ss"));//26-OCT-12 12.06.47.000000 PM 
                   dateFormats.add(new SimpleDateFormat("dd-MMM-yy"));    //30-OCT-12      
    }

    public static Date StringParseFecha(String fecha) {
                   defFormats();
                   Date date = new Date();              
                   if (fecha!=null && fecha.equalsIgnoreCase("")==false){
                                   Iterator<DateFormat> i = dateFormats.iterator();                          
                                   while(i.hasNext()){
                                                   try {
                                                       date = i.next().parse(fecha);     
                                                       return date;
                                                   } catch (ParseException e) {

                                                   }                                             
                                   }
                                   System.out.println("No se pudo formatear fecha "+fecha);
                   }
                   return date;
    }

    public static String FechaParseString(Date fecha, String Format) {
                   defFormats();
                   if (fecha!=null){
                                   DateFormat dateFormatParameter = new SimpleDateFormat(Format);              
                                   Iterator<DateFormat> i = dateFormats.iterator();                          
                                   while(i.hasNext()){
                                                   String Fdate = dateFormatParameter.format(fecha);
                                                   return Fdate;                                    
                                   }
                                   System.out.println("No se pudo formatear fecha "+fecha);
                   }
                   return "";
    }  
    
}
