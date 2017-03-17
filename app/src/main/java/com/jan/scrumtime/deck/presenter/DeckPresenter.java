package com.jan.scrumtime.deck.presenter;

import com.jan.mvp.BasePresenter;
import com.jan.mvp.BaseView;

/**
 * TODO: write class description
 */

public class DeckPresenter extends BasePresenter<DeckPresenter.DeckView> {

    private DeckView deckView;

    public DeckPresenter(DeckView deckView) {
        this.deckView = deckView;
    }

    @Override public DeckView getViewForBinding() {
        return deckView;
    }

    @Override public void updateView() {

    }

    @Override public void resetState() {

    }

    public void loadDeck() {

    }

    public void revealCard() {

    }

    public void cancelCard() {

    }

    public interface DeckView extends BaseView {

        void setDeck();

        void resetDeck();

        void showCard();

        void showFaceDownCard();
    }
}
