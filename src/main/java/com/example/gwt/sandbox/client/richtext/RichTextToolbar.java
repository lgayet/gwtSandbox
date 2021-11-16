package com.example.gwt.sandbox.client.richtext;

/**
 * Inspiré de la démo GWT Showcase
 */

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.i18n.client.Constants;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * A sample toolbar for use with {@link RichTextArea}. It provides a simple UI
 * for all rich text formatting, dynamically displayed only for the available
 * functionality.
 */
@SuppressWarnings("deprecation")
public class RichTextToolbar extends Composite {

    /**
     * This {@link ClientBundle} is used for all the button icons. Using a bundle
     * allows all of these images to be packed into a single image, which saves a
     * lot of HTTP requests, drastically improving startup time.
     */
    public interface Images extends ClientBundle {

        ImageResource bold();

        ImageResource createLink();

        ImageResource hr();

        ImageResource indent();

        ImageResource insertImage();

        ImageResource italic();

        ImageResource justifyCenter();

        ImageResource justifyLeft();

        ImageResource justifyRight();

        ImageResource ol();

        ImageResource outdent();

        ImageResource removeFormat();

        ImageResource removeLink();

        ImageResource strikeThrough();

        ImageResource subscript();

        ImageResource superscript();

        ImageResource ul();

        ImageResource underline();
    }

    /**
     * This {@link Constants} interface is used to make the toolbar's strings
     * internationalizable.
     */
    public interface Strings extends Constants {

        String black();

        String blue();

        String bold();

        String color();

        String font();

        String green();

        String hr();

        String indent();

        String italic();

        String justifyCenter();

        String justifyLeft();

        String justifyRight();

        String large();

        String medium();

        String normal();

        String ol();

        String outdent();

        String red();

        String size();

        String small();

        String strikeThrough();

        String subscript();

        String superscript();

        String ul();

        String underline();

        String white();

        String xlarge();

        String xsmall();

        String xxlarge();

        String xxsmall();

        String yellow();
    }

    /**
     * We use an inner EventHandler class to avoid exposing event methods on the
     * RichTextToolbar itself.
     */
    private class EventHandler implements ClickHandler, ChangeHandler,
            KeyUpHandler {

        public void onChange(ChangeEvent event) {
            Widget sender = (Widget) event.getSource();

            if (sender == backColors) {
                basic.setBackColor(backColors.getValue(backColors.getSelectedIndex()));
            } else if (sender == foreColors) {
                basic.setForeColor(foreColors.getValue(foreColors.getSelectedIndex()));
            } else if (sender == fonts) {
                basic.setFontName(fonts.getValue(fonts.getSelectedIndex()));
                fonts.setSelectedIndex(0);
            } else if (sender == fontSizes) {
                basic.setFontSize(fontSizesConstants[fontSizes.getSelectedIndex() - 1]);
                fontSizes.setSelectedIndex(0);
            }
        }

        public void onClick(ClickEvent event) {
            Widget sender = (Widget) event.getSource();

            if (sender == bold) {
                basic.toggleBold();
            } else if (sender == italic) {
                basic.toggleItalic();
            } else if (sender == underline) {
                basic.toggleUnderline();
            } else if (sender == subscript) {
                basic.toggleSubscript();
            } else if (sender == superscript) {
                basic.toggleSuperscript();
            } else if (sender == strikethrough) {
                basic.toggleStrikethrough();
            } else if (sender == indent) {
                basic.rightIndent();
            } else if (sender == outdent) {
                basic.leftIndent();
            } else if (sender == justifyLeft) {
                basic.setJustification(RichTextArea.Justification.LEFT);
            } else if (sender == justifyCenter) {
                basic.setJustification(RichTextArea.Justification.CENTER);
            } else if (sender == justifyRight) {
                basic.setJustification(RichTextArea.Justification.RIGHT);
            } else if (sender == hr) {
                basic.insertHorizontalRule();
            } else if (sender == ol) {
                basic.insertOrderedList();
            } else if (sender == ul) {
                basic.insertUnorderedList();
            } else if (sender == richText) {
                // We use the RichTextArea's onKeyUp event to update the toolbar status.
                // This will catch any cases where the user moves the cursur using the
                // keyboard, or uses one of the browser's built-in keyboard shortcuts.
                updateStatus();
            }
        }

        public void onKeyUp(KeyUpEvent event) {
            Widget sender = (Widget) event.getSource();
            if (sender == richText) {
                // We use the RichTextArea's onKeyUp event to update the toolbar status.
                // This will catch any cases where the user moves the cursur using the
                // keyboard, or uses one of the browser's built-in keyboard shortcuts.
                updateStatus();
            }
        }
    }

    private static final RichTextArea.FontSize[] fontSizesConstants = new RichTextArea.FontSize[] {
            RichTextArea.FontSize.XX_SMALL, RichTextArea.FontSize.X_SMALL,
            RichTextArea.FontSize.SMALL, RichTextArea.FontSize.MEDIUM,
            RichTextArea.FontSize.LARGE, RichTextArea.FontSize.X_LARGE,
            RichTextArea.FontSize.XX_LARGE};

    private Images images = (Images) GWT.create(Images.class);
    private Strings strings = (Strings) GWT.create(Strings.class);
    private EventHandler handler = new EventHandler();

    private RichTextArea richText;
    private RichTextArea.Formatter basic;

    private VerticalPanel outer = new VerticalPanel();
    private FlowPanel toolBarPanel = new FlowPanel();
    private ToggleButton bold;
    private ToggleButton italic;
    private ToggleButton underline;
    private ToggleButton subscript;
    private ToggleButton superscript;
    private ToggleButton strikethrough;
    private PushButton indent;
    private PushButton outdent;
    private PushButton justifyLeft;
    private PushButton justifyCenter;
    private PushButton justifyRight;
    private PushButton hr;
    private PushButton ol;
    private PushButton ul;

    private ListBox backColors;
    private ListBox foreColors;
    private ListBox fonts;
    private ListBox fontSizes;

    /**
     * Creates a new toolbar that drives the given rich text area.
     *
     * @param richText the rich text area to be controlled
     */
    public RichTextToolbar(RichTextArea richText) {
        this.richText = richText;
        this.basic = richText.getFormatter();

        outer.add(toolBarPanel);
        toolBarPanel.setWidth("100%");

        initWidget(outer);
        setStyleName("gwt-RichTextToolbar");
        richText.addStyleName("hasRichTextToolbar");

        toolBarPanel.add(bold = createToggleButton(images.bold(), strings.bold()));
        toolBarPanel.add(italic = createToggleButton(images.italic(), strings.italic()));
        toolBarPanel.add(underline = createToggleButton(images.underline(), strings.underline()));
        toolBarPanel.add(strikethrough = createToggleButton(images.strikeThrough(),
                strings.strikeThrough()));

        toolBarPanel.add(new Spacer());
        toolBarPanel.add(subscript = createToggleButton(images.subscript(),
                strings.subscript()));
        toolBarPanel.add(superscript = createToggleButton(images.superscript(),
                strings.superscript()));
        toolBarPanel.add(new Spacer());
        toolBarPanel.add(justifyLeft = createPushButton(images.justifyLeft(),
                strings.justifyLeft()));
        toolBarPanel.add(justifyCenter = createPushButton(images.justifyCenter(),
                strings.justifyCenter()));
        toolBarPanel.add(justifyRight = createPushButton(images.justifyRight(),
                strings.justifyRight()));
        toolBarPanel.add(new Spacer());


        toolBarPanel.add(indent = createPushButton(images.indent(), strings.indent()));
        toolBarPanel.add(outdent = createPushButton(images.outdent(),
                strings.outdent()));
        toolBarPanel.add(new Spacer());
        toolBarPanel.add(hr = createPushButton(images.hr(), strings.hr()));
        toolBarPanel.add(ol = createPushButton(images.ol(), strings.ol()));
        toolBarPanel.add(ul = createPushButton(images.ul(), strings.ul()));

        toolBarPanel.add(new Spacer());

        // We only use these handlers for updating status, so don't hook them up
        // unless at least basic editing is supported.
        richText.addKeyUpHandler(handler);
        richText.addClickHandler(handler);
    }


    private PushButton createPushButton(ImageResource img, String tip) {
        PushButton pb = new PushButton(new Image(img));
        pb.addClickHandler(handler);
        pb.setTitle(tip);
        return pb;
    }

    private ToggleButton createToggleButton(ImageResource img, String tip) {
        ToggleButton tb = new ToggleButton(new Image(img));
        tb.addClickHandler(handler);
        tb.setTitle(tip);
        return tb;
    }

    /**
     * Updates the status of all the stateful buttons.
     */
    private void updateStatus() {

        bold.setDown(basic.isBold());
        italic.setDown(basic.isItalic());
        underline.setDown(basic.isUnderlined());
        subscript.setDown(basic.isSubscript());
        superscript.setDown(basic.isSuperscript());
        strikethrough.setDown(basic.isStrikethrough());
    }

    private static class Spacer extends Widget {
        public Spacer() {
            SpanElement spanElement = Document.get().createSpanElement();
            setElement(spanElement);
            this.setWidth("8px");
            this.getElement().getStyle().setDisplay(Style.Display.INLINE_BLOCK);
        }
    }
}
