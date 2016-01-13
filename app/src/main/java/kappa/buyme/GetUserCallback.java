package kappa.buyme;

/**
 * Created by Taehyon on 6/22/2015.
 */

interface GetUserCallback {

    /**
     * Invoked when background task is completed
     */

    public abstract void done(User returnedUser);
}