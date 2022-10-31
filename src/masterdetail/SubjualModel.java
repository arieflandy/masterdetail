/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package masterdetail;

/**
 *
 * @author Administrator
 */
public class SubjualModel {
    
    private String nofaktur,kodebrg;
    private int jumlah;

    public SubjualModel(String nofaktur, String kodebrg, int jumlah) {
        this.nofaktur = nofaktur;
        this.kodebrg = kodebrg;
        this.jumlah = jumlah;
    }

    public SubjualModel() {
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public String getKodebrg() {
        return kodebrg;
    }

    public void setKodebrg(String kodebrg) {
        this.kodebrg = kodebrg;
    }

    public String getNofaktur() {
        return nofaktur;
    }

    public void setNofaktur(String nofaktur) {
        this.nofaktur = nofaktur;
    }
        
}
