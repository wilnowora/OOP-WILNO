public class ManUn {
    private String Nama;
    private int NomorPunggung ;
    private String Asal;
   

    public ManUn(String inputNama, int inputNomorPunggung, String inputAsal){
        this.Nama = inputNama;
        this.NomorPunggung = inputNomorPunggung;
        this.Asal = inputAsal;
        

    }

    public String getNama() {
        return Nama;
    }
    public int getNomorPunggung(){
        return NomorPunggung;
    }
    public String getAsal(){
        return Asal;
    }
 
}