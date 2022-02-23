package com.example.mylibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class BookActivity extends AppCompatActivity {

    public static final String BOOK_ID_KEY = "bookId";

    private TextView txtBookName, txtAuthorBookAct, txtPages, txtLongDesc;
    private Button btnAddToCurrentlyReading, btnAddToWantToRead, btnAddToAlreadyRead, btnAddToFavorites;
    private ImageView imgBookCover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        initViews();

//        String longdesc = "A scientific expedition on an alien planet goes awry when one of its members is attacked by a giant native creature. She is saved by the expedition's SecUnit (Security Unit), a cyborg security agent. The SecUnit has secretly hacked the governor module that allows it to be controlled by humans and named itself \"Murderbot\", as its purpose is murder, but prefers to spend its time watching soap operas and is uncomfortable interacting with humans. The SecUnit has a vested interest in keeping its human clients safe and alive, since it wants to avoid discovery of its autonomy and has an especially grisly past expedition on its record. Murderbot soon discovers that information regarding hazardous fauna has been deleted from their survey packet of the planet. Further investigation reveals that some sections of their maps are missing as well. Meanwhile the PreservationAux survey team, led by Dr. Mensah, navigate their mixed feelings about the part machine, part human nature of their SecUnit. When they lose contact with the other known expedition, the DeltFall Group, Mensah leads a team to the opposite side of the planet to investigate. At the DeltFall habitat, Murderbot discovers that everyone there has been brutally murdered, and one of their three SecUnits destroyed. Murderbot disables the remaining two as they attack it, but is surprised when two others appear; it destroys one, and Mensah takes the other.\n" +
//                "\n" +
//                "Murderbot is seriously injured, and realizes that one of the rogue SecUnits has installed a combat override module into its neck. The Preservation scientists are able to remove it before it completes the data upload that would put Murderbot under the control of whoever has command over the other SecUnits. The team discovers that Murderbot is autonomous, and once malfunctioned and murdered fifty-seven people. The Preservation scientists mostly agree that, based on its protective behavior thus far, the SecUnit can be trusted. Remembering small incidents that now appear to be attempted sabotage, Murderbot and the group determine that there must be a third expedition on the planet, whose members are trying to eliminate DeltFall and Preservation for some reason. The Preservation scientists confirm that their HubSystem has been hacked, and flee their habitat before the mystery expedition they have dubbed \"EvilSurvey\" comes to kill them. The EvilSurvey team—GrayCris—leaves a message in the Preservation habitat inviting its scientists to meet at a rendezvous point to negotiate terms for their survival. Murderbot knows that GrayCris will never let them live, but the SecUnit has a plan. It makes an overture to GrayCris to negotiate for its own freedom, but this is a distraction while the Preservation scientists access the GrayCris HubSystem to activate their emergency beacon. The plan works, but Murderbot is injured protecting Mensah from the explosion of the launch. Later the SecUnit finds itself repaired, and retaining its memories and disabled governor module. Mensah has bought its contract, and plans to bring it back to Preservation's home base where it can legally live autonomously. Though grateful, Murderbot is reluctant to have its decisions made for it, and slips away on a cargo ship";
//
//
//        //TODO: Get the data from the recycler view in here
//        Book book = new Book(1, "Artificial Condition", "Martha Wells", 150, "https://images-na.ssl-images-amazon.com/images/I/81eP4T-BTwL.__BG0,0,0,0_FMpng_AC_UL600_SR378,600_.jpg",
//                "The Murderbot Diaries are back", longdesc);

        Intent intent = getIntent();
        if (null != intent) {
            int bookId = intent.getIntExtra(BOOK_ID_KEY, -1);
            if (bookId != -1) {
                Book incomingBook = Utils.getInstance(this).getBookById(bookId);
                if (null != incomingBook) {
                    setData(incomingBook);

                    handleAlreadyRead(incomingBook);
                    handleWantToReadBooks(incomingBook);
                    handleCurrentlyReadingBook(incomingBook);
                    handleFavoriteBooks(incomingBook);
                }
            }
        }
    }

    private void handleWantToReadBooks(final Book book) {
        ArrayList<Book> wantToReadBooks = Utils.getInstance(this).getWantToReadBooks();

        boolean existInWantToReadBooks = false;

        for (Book b: wantToReadBooks) {
            if (b.getId() == book.getId()) {
                existInWantToReadBooks = true;
            }
        }

        if (existInWantToReadBooks) {
            btnAddToWantToRead.setEnabled(false);
        }else {
            btnAddToWantToRead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utils.getInstance(BookActivity.this).addToWantToRead(book)) {
                        Toast.makeText(BookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BookActivity.this, WantToReadActivity.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(BookActivity.this, "Something wrong happened", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void handleFavoriteBooks(final Book book) {
        ArrayList<Book> favoriteBooks = Utils.getInstance(this).getFavoriteBooks();

        boolean existInFavoriteBooks = false;

        for (Book b: favoriteBooks) {
            if (b.getId() == book.getId()) {
                existInFavoriteBooks = true;
            }
        }

        if (existInFavoriteBooks) {
            btnAddToFavorites.setEnabled(false);
        }else {
            btnAddToFavorites.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utils.getInstance(BookActivity.this).addToFavorites(book)) {
                        Toast.makeText(BookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BookActivity.this,FavoriteBooksActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(BookActivity.this, "Something wrong happened, please try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void handleCurrentlyReadingBook(final Book book) {
        ArrayList<Book> currentlyReadingBooks = Utils.getInstance(this).getCurrentlyReadingBooks();

        boolean existInCurrentlyReadingBooks = false;

        for (Book b: currentlyReadingBooks) {
            if (b.getId() == book.getId()) {
                existInCurrentlyReadingBooks = true;
            }
        }

        if (existInCurrentlyReadingBooks) {
            btnAddToCurrentlyReading.setEnabled(false);
        }else {
            btnAddToCurrentlyReading.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utils.getInstance(BookActivity.this).addToCurrentlyReading(book)) {
                        Toast.makeText(BookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(BookActivity.this,CurrentlyReadingActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(BookActivity.this, "Something wrong happened, please try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    /*
        Enable and disable button
        Add the book to Already Read Book ArrayList
    */
    private void handleAlreadyRead(Book book) {
        ArrayList<Book> alreadyReadBooks = Utils.getInstance(this).getAlreadyReadBooks();

        boolean existInAlreadyReadBooks = false;

        for (Book b: alreadyReadBooks) {
            if (b.getId() == book.getId()) {
                existInAlreadyReadBooks = true;
            }
        }

        if (existInAlreadyReadBooks) {
            btnAddToAlreadyRead.setEnabled(false);
        }else {
            btnAddToAlreadyRead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utils.getInstance(BookActivity.this).addToAlreadyRead(book)) {
                        Toast.makeText(BookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BookActivity.this,AlreadyReadBookActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(BookActivity.this, "Something wrong happened, please try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void setData(Book book) {
        txtBookName.setText(book.getName());
        txtAuthorBookAct.setText(book.getAuthor());
        txtPages.setText(String.valueOf(book.getPages()));
        txtLongDesc.setText(book.getLongDesc());
        Glide.with(this)
                .asBitmap().load(book.getImageURL())
                .into(imgBookCover);
    }

    private void initViews() {
        txtAuthorBookAct = findViewById(R.id.txtAuthorBookAct);
        txtBookName = findViewById(R.id.txtBookName);
        txtPages = findViewById(R.id.txtPages);
        txtLongDesc = findViewById(R.id.txtLongDesc);

        btnAddToCurrentlyReading = findViewById(R.id.btnAddToCurrentlyReading);
        btnAddToWantToRead = findViewById(R.id.btnAddToWantToRead);
        btnAddToAlreadyRead = findViewById(R.id.btnAddToAlreadyRead);
        btnAddToFavorites = findViewById(R.id.btnAddToFavorites);

        imgBookCover = findViewById(R.id.imgBookCover);
    }
}