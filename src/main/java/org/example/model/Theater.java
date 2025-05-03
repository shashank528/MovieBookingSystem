package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@AllArgsConstructor
@Getter
public class Theater {
    int theaterId;
    String theaterName;
    List<Screen> screens;
    public Theater(@NonNull final int theaterId,@NonNull final String theaterName)
    {
        this.theaterId = theaterId;
        this.theaterName = theaterName;
        screens = new ArrayList<>();
    }
    public void addScreen(Screen screen)
    {
        this.screens.add(screen);
    }
    public void deleteScreen(Screen screen)
    {
        this.screens.remove(screen);
    }
}
