package nicolae.fishingrecord.Data;

import org.joda.time.DateTime;

import java.io.Serializable;

public class FishPicture implements Serializable {

    private int id;
    private FishSpecie specie;
    private String imagePath;
    private String catchLocation;
    private DateTime catchDate;
    private String length;
    private String weight;


    public FishPicture(){

    }


    public FishPicture(String imagePath){
        this.imagePath = imagePath;
    }


    public FishPicture(FishSpecie specie, String imagePath, String catchLocation, DateTime catchDate, String length, String weight) {
        this.specie = specie;
        this.imagePath = imagePath;
        this.catchLocation = catchLocation;
        this.catchDate = catchDate;
        this.length = length;
        this.weight = weight;
    }


    public FishPicture(int id, FishSpecie specie, String imagePath, String catchLocation, DateTime catchDate, String length, String weight) {
        this.id = id;
        this.specie = specie;
        this.imagePath = imagePath;
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

    public String getImagePath() {
        return imagePath;
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

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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
