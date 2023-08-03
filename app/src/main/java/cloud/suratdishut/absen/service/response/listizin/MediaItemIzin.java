package cloud.suratdishut.absen.service.response.listizin;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MediaItemIzin {

	@SerializedName("manipulations")
	private List<Object> manipulations;

	@SerializedName("order_column")
	private int orderColumn;

	@SerializedName("file_name")
	private String fileName;

	@SerializedName("model_type")
	private String modelType;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("model_id")
	private int modelId;

	@SerializedName("custom_properties")
	private List<Object> customProperties;

	@SerializedName("uuid")
	private String uuid;

	@SerializedName("conversions_disk")
	private String conversionsDisk;

	@SerializedName("disk")
	private String disk;

	@SerializedName("size")
	private int size;

	@SerializedName("generated_conversions")
	private List<Object> generatedConversions;

	@SerializedName("responsive_images")
	private List<Object> responsiveImages;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("mime_type")
	private String mimeType;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("collection_name")
	private String collectionName;

	public List<Object> getManipulations(){
		return manipulations;
	}

	public int getOrderColumn(){
		return orderColumn;
	}

	public String getFileName(){
		return fileName;
	}

	public String getModelType(){
		return modelType;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public int getModelId(){
		return modelId;
	}

	public List<Object> getCustomProperties(){
		return customProperties;
	}

	public String getUuid(){
		return uuid;
	}

	public String getConversionsDisk(){
		return conversionsDisk;
	}

	public String getDisk(){
		return disk;
	}

	public int getSize(){
		return size;
	}

	public List<Object> getGeneratedConversions(){
		return generatedConversions;
	}

	public List<Object> getResponsiveImages(){
		return responsiveImages;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public String getMimeType(){
		return mimeType;
	}

	public String getName(){
		return name;
	}

	public int getId(){
		return id;
	}

	public String getCollectionName(){
		return collectionName;
	}
}