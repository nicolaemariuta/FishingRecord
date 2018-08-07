package nicolae.fishingrecord.Data;

import org.joda.time.DateTime;

import java.io.Serializable;

public class FishPicture implements Serializable {

    private int id;
    private FishSpecie specie;
    private String imageUrl;
    private String catchLocation;
    private DateTime catchDate;
    private String length;
    private String weight;

    public FishPicture(){

    }

    public FishPicture(String imageUrl){
        this.imageUrl = imageUrl;
    }


    public FishPicture(FishSpecie specie, String imageUrl, String catchLocation, DateTime catchDate, String length, String weight) {
        this.specie = specie;
        this.imageUrl = imageUrl;
        this.catchLocation = catchLocation;
        this.catchDate = catchDate;
        this.length = length;
        this.weight = weight;
    }


    public FishPicture(int id, FishSpecie specie, String imageUrl, String catchLocation, DateTime catchDate, String length, String weight) {
        this.id = id;
        this.specie = specie;
        this.imageUrl = imageUrl;
        this.catchLocation = catchLocation;
        this.catchDate = catchDate;
        this.length = length;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public FishSpecie getSpecie() {
        return specie;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getCatchLocation() {
        return catchLocation;
    }

    public DateTime getCatchDate() {
        return catchDate;
    }

    public String getLength() {
        return length;
    }

    public String getWeight() {
        return weight;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSpecie(FishSpecie specie) {
        this.specie = specie;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setCatchLocation(String catchLocation) {
        this.catchLocation = catchLocation;
    }

    public void setCatchDate(DateTime catchDate) {
        this.catchDate = catchDate;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
