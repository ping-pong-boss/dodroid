package doit.study.droid.adapters;

import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import doit.study.droid.data.Question;
import doit.study.droid.fragments.InterrogatorFragment;
import timber.log.Timber;


public class QuestionsPagerAdapter extends FragmentStatePagerAdapter {
    private static final boolean DEBUG = false;
    private Cursor mCursor;
    private Question mQuestion;

    public QuestionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        if (DEBUG) Timber.d("getItem, pos=%d", position);
        mCursor.moveToPosition(position);
        mQuestion = Question.newInstance(mCursor);
        Fragment fragment = InterrogatorFragment.newInstance(mQuestion);
        return fragment;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position){
        if (DEBUG) Timber.d("instantiateItem, pos=%d", position);
        return super.instantiateItem(container, position);
    }


    @Override
    public int getCount() {
        if (DEBUG) Timber.d("counter: %d", (mCursor == null ? 0 : mCursor.getCount()));
        if (mCursor != null)
            return mCursor.getCount();
        else
            return 0;
    }

    // don't know why, but getPageTitle called before getItem
    @Override
    public CharSequence getPageTitle(int position) {
        if (DEBUG) Timber.d("title pos: %d, questions: %s", position, mQuestion);
        StringBuffer title = new StringBuffer();
        // at exit pager asks title, cursor invalid
        if (mCursor != null) {
            mCursor.moveToPosition(position);
            for (String tag : Question.newInstance(mCursor).getTags())
                title.append(tag).append(" ");
            title.append(String.format(" %d/%d", position + 1, getCount()));
        }
        return title;
    }


    public void swapCursor(Cursor newCursor) {
        if (DEBUG) Timber.d("swap cursor, cnt: %d", newCursor != null ? newCursor.getCount() : 0);
        mCursor = newCursor;
        notifyDataSetChanged();
    }
}
