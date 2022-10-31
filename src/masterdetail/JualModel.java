/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package masterdetail;

import java.sql.Date;

/**
 *
 * @author Administrator
 */
public class JualModel {
    
    private String nofaktur,kodelgn;      
    private Date tanggal; //atribut
    public JualModel() {    } //constructor parameter kosong //constructor parameter semua attribut
    public JualModel(String nofaktur, String kodelgn, Date tanggal) {
    this.nofaktur=nofaktur;
    this.kodelgn=kodelgn;
    this.tanggal=tanggal;  
    }
    
    //sisanya tambahkan getter dan setter untuk semua attribute

    public String getKodelgn() {
        return kodelgn;
    }

    public void setKodelgn(String kodelgn) {
        this.kodelgn = kodelgn;
    }

    public String getNofaktur() {
        return nofaktur;
    }

    public void setNofaktur(String nofaktur) {
        this.nofaktur = nofaktur;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

}
