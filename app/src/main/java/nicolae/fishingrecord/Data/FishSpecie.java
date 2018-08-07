package nicolae.fishingrecord.Data;

import android.content.Context;

import nicolae.fishingrecord.R;

public enum FishSpecie {

    COD(R.string.specie_cod),
    COALFISH(R.string.specie_coalfish),
    FLOUNDER(R.string.specie_flounder),
    HERRING(R.string.specie_herring),
    MACKEREL(R.string.specie_mackerel),
    PIKE(R.string.specie_pike),
    WHITING(R.string.specie_whiting);


    private int nameStringId;


    FishSpecie(int nameStringId) {
        this.nameStringId = nameStringId;
    }


    public int getNameStringId() {
        return nameStringId;
    }

    public static String[] getAllFishNames(Context context){

        FishSpecie[] allValues = FishSpecie.values();
        String[] fishSpecies = new String[allValues.length];

        for(int i = 0; i < fishSpecies.length; i++)
            fishSpecies[i] = context.getResources().getString(allValues[i].nameStringId);

        return fishSpecies;

    }

}
