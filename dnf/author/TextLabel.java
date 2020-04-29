package dnf.author;

import java.math.BigDecimal;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import dnf.character.Character;
import dnf.gupoublex.GuPoubleXGame;
import dnf.gupoublex.set.SetHUD;
import dnf.hud.info.UpDownImage;

public class TextLabel extends Label {
	private Label backLD = null;
	private Label backLM = null;
	private Label backLT = null;
	private Label backMT = null;
	private Label backMD = null;
	private Label backRT = null;
	private Label backRM = null;
	private Label backRD = null;
	private Character ch = null;
	private String property = null;
	private float current = 0;
	private float time = 0;
	private UpDownImage cimg = null;
	private GuPoubleXGame game = null;
	private boolean group = false;
	public TextLabel(Character ch, String property, GuPoubleXGame game) {
		super("", new Label.LabelStyle(game.getLazy(), Color.WHITE));
		this.ch = ch;
		this.property = property;
		this.game = game;
		LabelStyle lablestyle = new LabelStyle(getStyle().font, Color.BLACK);
		getStyle().font.getData().markupEnabled = true;//change color
		backLD = new Label("", lablestyle);
		backLM = new Label("", lablestyle);
		backLT = new Label("", lablestyle);
		backMT = new Label("", lablestyle);
		backMD = new Label("", lablestyle);
		backRT = new Label("", lablestyle);
		backRM = new Label("", lablestyle);
		backRD = new Label("", lablestyle);
		setInfo(this);
		setInfo(backLD);
		setInfo(backLM);
		setInfo(backLT);
		setInfo(backMT);
		setInfo(backMD);
		setInfo(backRT);
		setInfo(backRM);
		setInfo(backRD);
		current = ch.getProperty(property);
		float current_tmp = 0;
		boolean hmp = false;
		if(property.equals("HP")) {
			hmp = true;
			current_tmp = ch.getProperty("HPMAX");
		}else if(property.equals("MP")) {
			hmp = true;
			current_tmp = ch.getProperty("MPMAX");
		}
		boolean is = isFloat();
		String str = "";
		if(is) {
			BigDecimal bd = new BigDecimal((float) current);
			bd = bd.setScale(1, 4);
			current = bd.floatValue();
			str = (current<0?"[RED]":"")+current+(current<0?"[]":"")+"%";
		}else 
			str = (current<0?"[RED]":"")+Integer.toString((int) current)+(current<0?"[]":"")+
					(hmp?"/"+Integer.toString((int) current_tmp):"");
		setText(str);
	}
	public TextLabel(GuPoubleXGame game) {
		super("", new Label.LabelStyle(game.getLazy(), Color.WHITE));
		LabelStyle lablestyle = new LabelStyle(getStyle().font, Color.BLACK);
		getStyle().font.getData().markupEnabled = true;//change color
		backLD = new Label("", lablestyle);
		backLM = new Label("", lablestyle);
		backLT = new Label("", lablestyle);
		backMT = new Label("", lablestyle);
		backMD = new Label("", lablestyle);
		backRT = new Label("", lablestyle);
		backRM = new Label("", lablestyle);
		backRD = new Label("", lablestyle);
		setInfo(this);
		setInfo(backLD);
		setInfo(backLM);
		setInfo(backLT);
		setInfo(backMT);
		setInfo(backMD);
		setInfo(backRT);
		setInfo(backRM);
		setInfo(backRD);
	}
	public void addActions(Vector2 v, float time) {
		setAction(this, v, time);
		setAction(backLD, v, time);
		setAction(backLM, v, time);
		setAction(backLT, v, time);
		setAction(backMT, v, time);
		setAction(backRT, v, time);
		setAction(backRM, v, time);
		setAction(backRD, v, time);
		setAction(backMD, v, time);
	}
	private void setAction(Label label, Vector2 v, float time) {
		MoveToAction moveto = Actions.moveTo(v.x, v.y, time, Interpolation.circleOut);
		SequenceAction sequence = Actions.sequence(moveto);
		label.addAction(sequence);
	}
	public TextLabel(CharSequence text, LabelStyle style, Color color) {
		super(text, style);
		LabelStyle lablestyle = new LabelStyle(style.font, color);
		backLD = new Label(text, lablestyle);
		backLM = new Label(text, lablestyle);
		backLT = new Label(text, lablestyle);
		backMT = new Label(text, lablestyle);
		backMD = new Label(text, lablestyle);
		backRT = new Label(text, lablestyle);
		backRM = new Label(text, lablestyle);
		backRD = new Label(text, lablestyle);
		setInfo(this);
		setInfo(backLD);
		setInfo(backLM);
		setInfo(backLT);
		setInfo(backMT);
		setInfo(backMD);
		setInfo(backRT);
		setInfo(backRM);
		setInfo(backRD);
	}
	@Override
	public void clearListeners() {
		super.clearListeners();
		backLD.clearListeners();
		backLM.clearListeners();
		backLT.clearListeners();
		backMT.clearListeners();
		backRT.clearListeners();
		backRM.clearListeners();
		backRD.clearListeners();
		backMD.clearListeners();
	}
	private void setInfo(Label label) {
		label.setWidth(100.0f);
		label.setHeight(20.0f);
		label.setAlignment(Align.center);
	}
	@Override
	public void setWrap(boolean wrap) {
		super.setWrap(wrap);
		backLD.setWrap(wrap);
		backLM.setWrap(wrap);
		backLT.setWrap(wrap);
		backMT.setWrap(wrap);
		backRT.setWrap(wrap);
		backRM.setWrap(wrap);
		backRD.setWrap(wrap);
		backMD.setWrap(wrap);
	}
	@Override
	public void setAlignment(int labelalign, int linealign) {
		super.setAlignment(labelalign, linealign);
		backLD.setAlignment(labelalign, linealign);
		backLM.setAlignment(labelalign, linealign);
		backLT.setAlignment(labelalign, linealign);
		backMT.setAlignment(labelalign, linealign);
		backRT.setAlignment(labelalign, linealign);
		backRM.setAlignment(labelalign, linealign);
		backRD.setAlignment(labelalign, linealign);
		backMD.setAlignment(labelalign, linealign);
	}
	@Override
	public void setFontScale(float fontScale) {
		super.setFontScale(fontScale);
		backLD.setFontScale(fontScale);
		backLM.setFontScale(fontScale);
		backLT.setFontScale(fontScale);
		backMT.setFontScale(fontScale);
		backRT.setFontScale(fontScale);
		backRM.setFontScale(fontScale);
		backRD.setFontScale(fontScale);
		backMD.setFontScale(fontScale);
	}
	@Override
	public void setSize(float width, float height) {
		super.setSize(width, height);
		backLD.setSize(width, height);
		backLM.setSize(width, height);
		backLT.setSize(width, height);
		backMT.setSize(width, height);
		backMD.setSize(width, height);
		backRT.setSize(width, height);
		backRM.setSize(width, height);
		backRD.setSize(width, height);
	}
	@Override
	public void setText(CharSequence newText) {
		if(newText.toString().equals(""))
			return;
		super.setText(newText);
		String str = clearColour(newText.toString());
		backLD.setText(str);
		backLM.setText(str);
		backLT.setText(str);
		backMT.setText(str);
		backRT.setText(str);
		backRM.setText(str);
		backRD.setText(str);
		backMD.setText(str);
	}
	private String clearColour(String str) {
		if(str.indexOf("[]") != -1) {
			String str_start = String.copyValueOf(str.toCharArray(), 0, str.indexOf("["));
			String str_end = String.copyValueOf(str.toCharArray(), str.indexOf("]")+1, str.length()-str.indexOf("]")-1);
			str = str_start+str_end;
			str_start = String.copyValueOf(str.toCharArray(), 0, str.indexOf("["));
			str_end = String.copyValueOf(str.toCharArray(), str.indexOf("]")+1, str.length()-str.indexOf("]")-1);
			str = str_start+str_end;
			if(str.indexOf("[]") != -1)
				str = clearColour(str);
		}
		return str;
	}
	@Override
	public void setPosition(float x, float y) {
		super.setPosition(x, y);
		backLD.setPosition(x-1, y+1);backMD.setPosition(x, y+1);backRD.setPosition(x+1, y+1);
		backLM.setPosition(x-1, y  );							backRM.setPosition(x+1, y  );
		backLT.setPosition(x-1, y-1);backMT.setPosition(x, y-1);backRT.setPosition(x+1, y-1);
		if(cimg != null)
			cimg.setPosition(getX()+getWidth()+4, getY()+getHeight()/2-cimg.getHeight()/2);
	}
	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		backLD.setVisible(visible);backMD.setVisible(visible);backRD.setVisible(visible);
		backLM.setVisible(visible);							  backRM.setVisible(visible);
		backLT.setVisible(visible);backMT.setVisible(visible);backRT.setVisible(visible);
	}
	public void addGroup(Group g) {
		this.group = true;
		g.addActor(backLD);
		g.addActor(backMD);
		g.addActor(backRD);
		g.addActor(backLM);
		g.addActor(backRM);
		g.addActor(backLT);
		g.addActor(backMT);
		g.addActor(backRT);
		g.addActor(this);
	}
	@Override
	public void act(float delta) {
		if(!group) {
			backLD.act(delta);backMD.act(delta);backRD.act(delta);
			backLM.act(delta);					backRM.act(delta);
			backLT.act(delta);backMT.act(delta);backRT.act(delta);
		}
		super.act(delta);
		time += delta;
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
		if(ch != null)
			if(time >= 0.2f) {
				time -= 0.2f;
				current = ch.getProperty(property);
				if(cimg != null) {
					float x = cimg.getX();
					float y = cimg.getY();
					cimg.remove();
					if(ch.getBuffProperty(property) > 0)
						cimg = new UpDownImage(game, true);
					else if(ch.getBuffProperty(property) < 0)
						cimg = new UpDownImage(game, false);
					else
						cimg = new UpDownImage(game);
					cimg.setPosition(x, y);
				}else {
					if(ch.getBuffProperty(property) > 0)
						cimg = new UpDownImage(game, true);
					else if(ch.getBuffProperty(property) < 0)
						cimg = new UpDownImage(game, false);
					else
						cimg = new UpDownImage(game);
					cimg.setPosition(getX()+getWidth()+4, getY()+getHeight()/2-cimg.getHeight()/2);
				}
				float current_tmp = 0;
				boolean hmp = false;
				if(property.equals("HP")) {
					hmp = true;
					current_tmp = ch.getProperty("HPMAX");
				}else if(property.equals("MP")) {
					hmp = true;
					current_tmp = ch.getProperty("MPMAX");
				}
				boolean is = isFloat();
				String str = "";
				if(is) {
					if(property.equals(SetHUD.value[12]) ||
							property.equals(SetHUD.value[13]) ||
							property.equals(SetHUD.value[14]))
						current *= 100;
					BigDecimal bd = new BigDecimal((float) current);
					bd = bd.setScale(1, 4);
					current = bd.floatValue();
					str = (current<0?"[RED]":"")+current+(current<0?"[]":"")+"%";
				}else 
					str = (current<0?"[RED]":"")+Integer.toString((int) current)+(current<0?"[]":"")+
							(hmp?"/"+Integer.toString((int) current_tmp):"");
				setText(str);
			}
		if(!group) {
			backLD.draw(batch, parentAlpha);backMD.draw(batch, parentAlpha);backRD.draw(batch, parentAlpha);
			backLM.draw(batch, parentAlpha);								backRM.draw(batch, parentAlpha);
			backLT.draw(batch, parentAlpha);backMT.draw(batch, parentAlpha);backRT.draw(batch, parentAlpha);
		}
		super.draw(batch, parentAlpha);
		if(cimg != null)
			cimg.draw(batch, parentAlpha);
	}
	private boolean isFloat() {
		boolean is = false;
		if(property.equals("CRT"))
			is = true;
		else if(property.equals("CRD"))
			is = true;
		else if(property.equals("SPHY"))
			is = true;
		else if(property.equals("SMIG"))
			is = true;
		else if(property.equals("SMOV"))
			is = true;
		return is;
	}
}
