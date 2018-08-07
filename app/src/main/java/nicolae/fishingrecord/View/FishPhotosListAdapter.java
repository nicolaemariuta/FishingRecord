package nicolae.fishingrecord.View;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.joda.time.format.DateTimeFormat;

import java.util.List;

import nicolae.fishingrecord.Data.FishPicture;
import nicolae.fishingrecord.R;

public class FishPhotosListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private List<FishPicture> fishPictures;

    private Context context;
    private FishPhotosListListener listListener;

    public FishPhotosListAdapter(Context context, List<FishPicture> fishPictures, FishPhotosListListener fishPhotosListListener) {
        this.fishPictures = fishPictures;
        this.context = context;
        this.listListener = fishPhotosListListener;
    }

    public void setFishPictures(List<FishPicture> newFishPictures) {
        this.fishPictures = newFishPictures;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fish_photo, parent, false);
        return new FishImageViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final FishPicture picture = fishPictures.get(i);
        FishImageViewHolder holder = (FishImageViewHolder) viewHolder;


        String urlStr = Uri.parse(picture.getImageUrl()).buildUpon().build().toString();
        Picasso.Builder builder = new Picasso.Builder(context);
        builder.listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                exception.printStackTrace();
            }
        });
        urlStr = "file:" + urlStr;
        builder.build().load(urlStr).into(holder.fishPicture);

        holder.fishSpecie.setText(picture.getSpecie().getNameStringId());
        holder.catchDate.setText(picture.getCatchDate().toString(DateTimeFormat.forPattern("dd/MM/yyyy")));
        holder.catchLength.setText(context.getResources().getString(R.string.item_photo_length, picture.getLength()));
        holder.catchWeight.setText(context.getResources().getString(R.string.item_photo_weight, picture.getWeight()));
        holder.catchLocation.setText(picture.getCatchLocation());

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listListener.deleteFishPicture(picture);
            }
        });

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listListener.clickedFishPicture(picture);
            }
        });
    }

    @Override
    public int getItemCount() {
        return fishPictures.size();
    }

    public void deletePicture(FishPicture picture) {
        int position = fishPictures.indexOf(picture);

        if(position != -1){
            fishPictures.remove(position);
            notifyItemRemoved(position);
        }
    }

    private static class FishImageViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout layout;
        private ImageView fishPicture;
        private TextView fishSpecie;
        private TextView catchDate;
        private TextView catchLength;
        private TextView catchWeight;
        private TextView catchLocation;
        private AppCompatImageView deleteButton;


        public FishImageViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = (RelativeLayout) itemView.findViewById(R.id.itemPhoto_layout);
            fishPicture = (ImageView)itemView.findViewById(R.id.itemPhoto_icon);
            fishSpecie = (TextView) itemView.findViewById(R.id.itemPhoto_specie);
            catchDate = (TextView) itemView.findViewById(R.id.itemPhoto_date);
            catchLength = (TextView) itemView.findViewById(R.id.itemPhoto_length);
            catchWeight = (TextView) itemView.findViewById(R.id.itemPhoto_weight);
            catchLocation= (TextView) itemView.findViewById(R.id.itemPhoto_location);
            deleteButton = (AppCompatImageView) itemView.findViewById(R.id.itemPhoto_delete);
        }
    }

    public interface FishPhotosListListener {

        void clickedFishPicture(FishPicture fishPicture);

        void deleteFishPicture(FishPicture fishPicture);
    }


}
