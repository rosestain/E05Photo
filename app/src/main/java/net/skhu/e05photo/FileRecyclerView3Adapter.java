package net.skhu.e05photo;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FileRecyclerView3Adapter extends RecyclerView.Adapter<FileRecyclerView3Adapter.ViewHolder> {
    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, PopupMenu.OnMenuItemClickListener
    {
        TextView textView1, textView2;
        ImageView imageView;
        ImageButton imageButton;

        public ViewHolder(View view)
        {
            super(view);
            textView1 = view.findViewById(R.id.textView1);
            textView2 = view.findViewById(R.id.textView2);
            imageView = view.findViewById(R.id.imageView);
            imageButton = view.findViewById(R.id.imageButton);
            view.setOnClickListener(this);
            imageButton.setOnClickListener(this);


        }

        public void setData()
        {
            File file = files.get(getAdapterPosition());
            textView1.setText(dateFormat.format(file.lastModified()));
            String s = String.format("%,d bytes", file.length());
            textView2.setText(s);
            imageView.setImageURI(Uri.fromFile(file));
        }

        @Override
        public void onClick(View view)
        {
            if(view == imageButton)
            {
                showPopupMenu(view);
            }
            else {
                int index = getAdapterPosition();
                File file = files.get(index);
                clickListener.onClick(index, file);
            }
        }

        private void showPopupMenu(View view)
        {
            PopupMenu popup = new PopupMenu(view.getContext(), view);
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.menu_photo_file, popup.getMenu());
            popup.setOnMenuItemClickListener(this);
            popup.show();
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if(item.getItemId() == R.id.action_delete)
            {
                int index = getAdapterPosition();
                files.get(index).delete();
                files.remove(index);
                notifyItemRemoved(index);
                return true;
            }
            else if(item.getItemId() == R.id.action_share)
            {
                int index = getAdapterPosition();
                File file = files.get(index);
                shareListener.onShareRequested(index, file);
            }
            return false;
        }
    }

    LayoutInflater layoutInflater;
    List<File> files;
    OnFileClickListener clickListener;
    OnFileShareListener shareListener;

    public FileRecyclerView3Adapter(Context context, File[] files, OnFileClickListener clickListener,
                                    OnFileShareListener shareListener)
    {
        this.layoutInflater = LayoutInflater.from(context);
        this.files = new ArrayList<>();
        this.files.addAll(Arrays.asList(files));
        Collections.sort(this.files, (f1, f2) -> (int)(f2.lastModified() - f1.lastModified()));
        this.clickListener = clickListener;
        this.shareListener = shareListener;

    }

    @Override
    public int getItemCount()
    {
        return files.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
    {
        View view = layoutInflater.inflate(R.layout.photo_file2, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int index)
    {
        viewHolder.setData();
    }

    public void add(File file)
    {
        files.add(0,file);
        notifyItemInserted(0);
    }
}
