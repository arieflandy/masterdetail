/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package masterdetail;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Administrator
 */
public class DBjual {
    private JualModel dt=new JualModel();    
    private HashMap<String,SubjualModel> dt2=new HashMap<String,SubjualModel>();
    
    public JualModel getJualModel(){ return(dt);}
    public void setJualModel(JualModel s){ dt=s;}

    public HashMap<String,SubjualModel> getSubjualModel(){ return(dt2);}
    public void setSubjualModel(SubjualModel d){ dt2.put(d.getKodebrg(), d);}

    public ObservableList<SubjualModel>  LoadDetil() {
        try {
            ObservableList<SubjualModel> tableData=FXCollections.observableArrayList();
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            dt2.clear();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("Select * from subjual where nofaktur = '"+getJualModel().getNofaktur()+"'");
            int i = 1;
            while (rs.next()) {
                SubjualModel d=new SubjualModel();
                d.setNofaktur(rs.getString("NoFaktur"));
                d.setKodebrg(rs.getString("Kodebrg"));
                d.setJumlah(rs.getInt("jumlah"));
                tableData.add(d);
                setSubjualModel(d);
                i++;
            }
            con.tutupKoneksi();
            return tableData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } 
    }
    
    public boolean saveall() {
        boolean berhasil = false;
        Koneksi con = new Koneksi();
        try {
            con.bukaKoneksi();
            con.dbKoneksi.setAutoCommit(false); // membuat semua perintah menjadi 1 transaksi
            con.preparedStatement = con.dbKoneksi.prepareStatement("delete from jual where NoFaktur=?");
            con.preparedStatement.setString(1, getJualModel().getNofaktur());
            con.preparedStatement.executeUpdate();           
            con.preparedStatement = con.dbKoneksi.prepareStatement("insert into jual (NoFaktur,tanggal, kodelgn) values (?,?,?)");
            con.preparedStatement.setString(1, getJualModel().getNofaktur());
            con.preparedStatement.setDate(2, (Date) getJualModel().getTanggal());
            con.preparedStatement.setString(3, getJualModel().getKodelgn());
            con.preparedStatement.executeUpdate();
            con.preparedStatement = con.dbKoneksi.prepareStatement("delete from subjual where NoFaktur=?");
            con.preparedStatement.setString(1, getJualModel().getNofaktur());
            con.preparedStatement.executeUpdate();           
            for(SubjualModel sm:dt2.values()){
            con.preparedStatement = con.dbKoneksi.prepareStatement("insert into subjual (NoFaktur,kodebrg, jumlah) values (?,?,?)");
            con.preparedStatement.setString(1, sm.getNofaktur());
            con.preparedStatement.setString(2, sm.getKodebrg());
            con.preparedStatement.setInt(3, sm.getJumlah());
            con.preparedStatement.executeUpdate();
            }
            con.dbKoneksi.commit(); //semua perintah di transaksi dikerjakan
            berhasil = true;
        } catch (Exception e) {
            e.printStackTrace();
            berhasil = false;
        } finally {
            con.tutupKoneksi();
            return berhasil;
        }
    }
    
 public JualModel getdata(String nomor) {
    JualModel tmp=new JualModel();
        try {
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery( "select * from jual where Nofaktur = '"+nomor+"'");
            while (rs.next()) {
                tmp.setNofaktur(rs.getString("nofaktur"));
                tmp.setTanggal(rs.getDate("tanggal"));
                tmp.setKodelgn(rs.getString("Kodelgn"));
            }
            con.tutupKoneksi();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tmp;   
}
public int validasi(String nomor) {
        int val = 0;
        try {
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery(
                    "select count(*) as jml from jual where Nofaktur = '" +
                            nomor +"'");
            while (rs.next()) {
                val = rs.getInt("jml");
            }
            con.tutupKoneksi();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return val;
    }
 


}