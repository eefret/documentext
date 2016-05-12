package com.okorana.documentext.ui.itemview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.TextAppearanceSpan;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import com.okorana.documentext.R;
import com.okorana.documentext.data.Contact;
import com.okorana.documentext.data.Conversation;
import com.okorana.documentext.utils.Utils;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by eefret on 5/8/16.
 */
@EViewGroup(R.layout.item_conversation_list)
public class ConversationItemView extends LinearLayout {
    static private Drawable sDefaultContactImage;
    @ViewById(R.id.selected) ImageView selected;
    @ViewById(R.id.conversation_list_avatar) QuickContactBadge avatar;
    @ViewById(R.id.conversation_list_name) TextView name;
    @ViewById(R.id.conversation_list_date) TextView date;
    @ViewById(R.id.conversation_list_snippet) TextView snippet;
    @ViewById(R.id.conversation_list_badges) LinearLayout badges;
    @ViewById(R.id.conversation_list_unread) ImageView unread;
    private Conversation mConversation;
    private com.okorana.documentext.phonebookdata.Conversation mphoneBookConversation;
    private Context context;
    public static final StyleSpan STYLE_BOLD = new StyleSpan(Typeface.BOLD);

    public ConversationItemView(Context context) {
        super(context);
        this.context = context;
        if (sDefaultContactImage == null) {
            sDefaultContactImage = context.getResources().getDrawable(R.drawable.ic_person);
        }
    }

    private void updateAvatarView() {
        Drawable avatarDrawable;
        if (mphoneBookConversation.getRecipients().size() == 1) {
            com.okorana.documentext.phonebookdata.Contact contact = mphoneBookConversation.getRecipients().get(0);
            avatarDrawable = contact.getAvatar(context, sDefaultContactImage);
            if(contact.existsInDatabase()){
                avatar.assignContactUri(contact.getUri());
            } else {
                avatar.assignContactFromPhone(contact.getNumber(), true);
            }
        } else {
            avatarDrawable = sDefaultContactImage;
            avatar.assignContactUri(null);
        }
        avatar.setImageDrawable(avatarDrawable);
        avatar.setVisibility(VISIBLE);
    }

    private CharSequence formatMessage() {
        String from = mphoneBookConversation.getRecipients().formatNames(", ");
        SpannableStringBuilder buf = new SpannableStringBuilder(from);
        if (mphoneBookConversation.getMessageCount() > 1) {
            int before = buf.length();
            buf.append(context.getResources().getString(R.string.message_count_format,
                    mphoneBookConversation.getMessageCount()));
            buf.setSpan(new ForegroundColorSpan(
                            context.getResources().getColor(R.color.message_count_color)),
                    before, buf.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        // Unread messages are shown in bold
        if (mphoneBookConversation.hasUnreadMessages()) {
            buf.setSpan(STYLE_BOLD, 0, buf.length(),
                    Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        return buf;
    }

    public void bind(Conversation conversation) {
        mConversation = conversation;
        mphoneBookConversation = com.okorana.documentext.phonebookdata.Conversation.get(context, conversation.get_id(), true);
        date.setText(Utils.formatTimeStampString(context, conversation.getDate(), false));
        updateAvatarView();
        name.setText(conversation.conversationName);
        snippet.setText(mphoneBookConversation.getSnippet());
    }

    public Conversation getConversation() {
        return mConversation;
    }
}
