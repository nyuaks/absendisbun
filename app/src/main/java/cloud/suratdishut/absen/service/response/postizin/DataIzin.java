package cloud.suratdishut.absen.service.response.postizin;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DataIzin {

	@SerializedName("jenis_absensi_id")
	private int jenisAbsensiId;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("pegawai_id")
	private int pegawaiId;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("url_file")
	private Object urlFile;

	@SerializedName("jumlah_hari")
	private int jumlahHari;

	@SerializedName("id")
	private int id;

	@SerializedName("media")
	private List<Object> media;

	@SerializedName("tertanggal")
	private String tertanggal;

	@SerializedName("status")
	private int status;

	public int getJenisAbsensiId(){
		return jenisAbsensiId;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public int getPegawaiId(){
		return pegawaiId;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public Object getUrlFile(){
		return urlFile;
	}

	public int getJumlahHari(){
		return jumlahHari;
	}

	public int getId(){
		return id;
	}

	public List<Object> getMedia(){
		return media;
	}

	public String getTertanggal(){
		return tertanggal;
	}

	public int getStatus(){
		return status;
	}
}