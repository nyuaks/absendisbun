package cloud.suratdishut.absen.service.response.listabsensi;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DataListAbsensi {

	@SerializedName("total_pulang")
	private int totalPulang;

	@SerializedName("total_masuk")
	private int totalMasuk;

	@SerializedName("absensi")
	private List<AbsensiItem> absensi;

	@SerializedName("total_dinas")
	private int totalDinas;

	@SerializedName("total_izin")
	private int totalIzin;

	@SerializedName("total_cuti")
	private int totalCuti;

	public int getTotalPulang(){
		return totalPulang;
	}

	public int getTotalMasuk(){
		return totalMasuk;
	}

	public List<AbsensiItem> getAbsensi(){
		return absensi;
	}

	public int getTotalDinas(){
		return totalDinas;
	}

	public int getTotalIzin(){
		return totalIzin;
	}

	public int getTotalCuti(){
		return totalCuti;
	}
}