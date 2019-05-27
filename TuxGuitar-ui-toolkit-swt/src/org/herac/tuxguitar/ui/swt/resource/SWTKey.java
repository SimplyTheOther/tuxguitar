package org.herac.tuxguitar.ui.swt.resource;

import org.eclipse.swt.SWT;
import org.herac.tuxguitar.ui.resource.UIKey;
import org.herac.tuxguitar.ui.resource.UIKeyCombination;

public class SWTKey {
	
	private static final SWTKeyMap[] KEY_MAP = new SWTKeyMap[] {
		new SWTKeyMap(SWT.F1, UIKey.F1),
		new SWTKeyMap(SWT.F2, UIKey.F2),
		new SWTKeyMap(SWT.F3, UIKey.F3),
		new SWTKeyMap(SWT.F4, UIKey.F4),
		new SWTKeyMap(SWT.F5, UIKey.F5),
		new SWTKeyMap(SWT.F6, UIKey.F6),
		new SWTKeyMap(SWT.F7, UIKey.F7),
		new SWTKeyMap(SWT.F8, UIKey.F8),
		new SWTKeyMap(SWT.F9, UIKey.F9),
		new SWTKeyMap(SWT.F10, UIKey.F10),
		new SWTKeyMap(SWT.F11, UIKey.F11),
		new SWTKeyMap(SWT.F12, UIKey.F12),
		new SWTKeyMap(SWT.ESC, UIKey.ESC),
		new SWTKeyMap(SWT.PAUSE, UIKey.PAUSE),
		new SWTKeyMap(SWT.PRINT_SCREEN, UIKey.PRINT_SCREEN),
		new SWTKeyMap(SWT.INSERT, UIKey.INSERT),
		new SWTKeyMap(SWT.DEL, UIKey.DELETE),
		new SWTKeyMap(SWT.HOME, UIKey.HOME),
		new SWTKeyMap(SWT.PAGE_UP, UIKey.PAGE_UP),
		new SWTKeyMap(SWT.PAGE_DOWN, UIKey.PAGE_DOWN),
		new SWTKeyMap(SWT.END, UIKey.END),
		new SWTKeyMap(SWT.ALT, UIKey.ALT),
		new SWTKeyMap(SWT.CONTROL, UIKey.CONTROL),
		new SWTKeyMap(SWT.COMMAND, UIKey.COMMAND),
		new SWTKeyMap(SWT.SHIFT, UIKey.SHIFT),
		new SWTKeyMap(SWT.TAB, UIKey.TAB),
		new SWTKeyMap(SWT.BS, UIKey.BACKSPACE),
		new SWTKeyMap(SWT.SPACE, UIKey.SPACE),
		new SWTKeyMap(0xd, UIKey.ENTER),
		new SWTKeyMap(0x1000050, UIKey.ENTER),
		new SWTKeyMap(0x1000001, UIKey.UP),
		new SWTKeyMap(0x1000002, UIKey.DOWN),
		new SWTKeyMap(0x1000003, UIKey.LEFT),
		new SWTKeyMap(0x1000004, UIKey.RIGHT)
	};
	
	public UIKey getKey(int keyCode) {
		for(SWTKeyMap keyMap : KEY_MAP) {
			if( keyMap.getCode() == keyCode ) {
				return keyMap.getKey();
			}
		}
		return new UIKey(Character.toString((char) (keyCode & 0xffff)));
	}
	
	public UIKeyCombination getCombination(int keyCode, int stateMask) {
		UIKeyCombination keyCombination = new UIKeyCombination();
		if((stateMask & keyCode) == 0) {
			if((stateMask & SWT.ALT) != 0) {
				keyCombination.getKeys().add(UIKey.ALT);
			}
			if((stateMask & SWT.SHIFT) != 0) {
				keyCombination.getKeys().add(UIKey.SHIFT);
			}
			if((stateMask & SWT.CONTROL) != 0) {
				keyCombination.getKeys().add(UIKey.CONTROL);
			}
			if((stateMask & SWT.COMMAND) != 0) {
				keyCombination.getKeys().add(UIKey.COMMAND);
			}
		}
		
		UIKey principalKey = this.getKey(keyCode);
		if(!keyCombination.contains(principalKey)) {
			keyCombination.getKeys().add(principalKey);
		}
		
		return keyCombination;
	}
	
	private static class SWTKeyMap {
		
		private int code;
		private UIKey key;
		
		public SWTKeyMap(int code, UIKey key) {
			this.code = code;
			this.key = key;
		}
		
		public int getCode() {
			return this.code;
		}
		
		public UIKey getKey() {
			return this.key;
		}
	}
}
