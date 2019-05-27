package org.herac.tuxguitar.graphics.control;

/**
 * The goal of this class is to provide a configurable interface to drum
 * rendering mapping of rendering flags and notes.
 * 
 * @author simpoir@gmail.com, Theo Other
 *
 */
//moved to tg-lib to not have tg as a dependency for tg-lib
//could potentially go in a different package, but this seemed the most appropriate to me
public class TGDrumMap {
	static final int MAX_NOTES = 90;
	static final int MAP_VALUE = 0;
	static final int MAP_KIND = 1;
	static final int MAP_LEN = 2;

	// master kinds
	public static final int KIND_CYMBAL = 0x1; // X note
	public static final int KIND_NOTE = 0x2; // round and black
	public static final int KIND_SLANTED_DIAMOND = 0x10; // slanted diamond - draw like harmonic
	public static final int KIND_TRIANGLE = 0x20; // triangle - like tambourine
	public static final int KIND_EFFECT_CYMBAL = 0x40; // weirdish-looking X
	
	// modifiers
	public static final int KIND_OPEN = 0x4; // small o above
	public static final int KIND_CLOSED = 0x8; // small + above
	public static final int KIND_CIRCLE_AROUND = 0x80; // circle around note head

	// note#, [render note#, render flags]
	private int[][] _mapping = new int[MAX_NOTES][MAP_LEN];

	// TODO discuss this (and refactor)
	// singleton. I'm not sure if it's the best behaviour, as it would
	// be nicer to have per song drum mapping, but for now global config it is.
	private static TGDrumMap _instance;

	// The note to return when mapping is undefined
	private int _defaultNote;

	/**
	 * Fetches the current drum mapping or create one from default settings.
	 * 
	 * As there is currently no loading or saving of this, it always returns the
	 * default hardcoded mapping.
	 * 
	 * @return an existing TGDrumMap or a new if none exists
	 */
	public static TGDrumMap getCurrentDrumMap() {
		if (_instance == null) {
			_instance = new TGDrumMap();
		}

		return _instance;
	}

	public TGDrumMap() {
		_loadDefaultMapping();
	}

	// TODO support dumping as pref and reloading drum maps
	private void _loadDefaultMapping() {
		_defaultNote = 0;

		// bass drums
		_mapping[35] = new int[] { 52, KIND_NOTE }; // acoustic bass drum 
		_mapping[36] = new int[] { 53, KIND_NOTE }; // bass drum 
		
		//snares
		_mapping[37] = new int[] { 60, KIND_NOTE | KIND_CIRCLE_AROUND }; //cross stick 
		_mapping[38] = new int[] { 60, KIND_NOTE }; // acoustic snare 
		_mapping[40] = new int[] { 60, KIND_NOTE }; // electric snare 
		
		//hi-hats
		_mapping[42] = new int[] { 67, KIND_CYMBAL | KIND_CLOSED }; // closed high hat 
		_mapping[46] = new int[] { 67, KIND_CYMBAL | KIND_OPEN }; // open high hat 
		_mapping[44] = new int[] { 50, KIND_CYMBAL }; // foot hi-hat
		
		//effect cymbals
		_mapping[49] = new int[] { 69, KIND_EFFECT_CYMBAL }; // crash cymbal 
		_mapping[57] = new int[] { 71, KIND_EFFECT_CYMBAL }; // crash cymbal 2 
		_mapping[55] = new int[] { 72, KIND_EFFECT_CYMBAL }; // splash cymbal 
		_mapping[52] = new int[] { 72, KIND_EFFECT_CYMBAL | KIND_CIRCLE_AROUND }; // china cymbal 
		
		//ride cymbals
		_mapping[51] = new int[] { 65, KIND_CYMBAL }; // ride cymbal 
		_mapping[59] = new int[] { 62, KIND_CYMBAL }; // ride cymbal 2 
		_mapping[53] = new int[] { 65, KIND_SLANTED_DIAMOND }; // ride bell 
		
		//toms
		_mapping[41] = new int[] { 55, KIND_NOTE }; // low floor tom 
		_mapping[43] = new int[] { 57, KIND_NOTE }; // high floor tom 
		_mapping[45] = new int[] { 59, KIND_NOTE }; // low tom
		_mapping[47] = new int[] { 62, KIND_NOTE }; // low-mid tom
		_mapping[48] = new int[] { 64, KIND_NOTE }; // hi-mid tom 
		_mapping[50] = new int[] { 65, KIND_NOTE }; // high tom 
		
		//misc percussion
		_mapping[54] = new int[] { 59, KIND_TRIANGLE }; // tambourine 
		_mapping[56] = new int[] { 64, KIND_TRIANGLE }; // cowbell 
		_mapping[31] = new int[] { 59, KIND_CYMBAL }; // click sticks
		_mapping[77] = new int[] { 60, KIND_TRIANGLE }; // low wood block
		_mapping[76] = new int[] { 62, KIND_TRIANGLE }; // high wood block
		_mapping[81] = new int[] { 69, KIND_TRIANGLE }; // open triangle
		_mapping[80] = new int[] { 69, KIND_TRIANGLE | KIND_CLOSED }; // mute triangle
		//add more misc percussion
		
		/* Note: Many misc percussion instruments were not added to the map
		 * because there doesn't seem to be a consensus on where they should
		 * go on the stave. The TG community could decide their positions.
		 * The list is here:
		 * 27 High Q
		 * 28 Slap
		 * 29 Scratch Push
		 * 30 Scratch Pull
		 * 32 Square Click
		 * 33 Metronome Click
		 * 34 Metronome Bell
		 * 39 Hand Clap
		 * 58 Vibraslap
		 * 60 High Bongo
		 * 61 Low Bongo
		 * 62 Mute High Conga
		 * 63 Open High Conga
		 * 64 Low Conga
		 * 65 High Timbale
		 * 66 Low Timbale
		 * 67 High Agogo
		 * 68 Low Agogo
		 * 69 Cabasa
		 * 70 Maracas
		 * 71 Short Whistle
		 * 72 Long Whistle
		 * 73 Short Guiro
		 * 74 Long Guiro
		 * 75 Claves
		 * 78 Mute Cuica
		 * 79 Open Cuica
		 * 82 Shaker
		 * 83 Jingle Bell
		 * 84 Belltree
		 * 85 Castanets
		 * 86 Mute Surdo
		 * 87 Open Surdo
		 * */
	}
	
	/**
	 * Returns the drum mapped equivalent of value, or a default note if not defined.
	 * @param value a note value to be transposed
	 * @return the numeric value of the transposed note.
	 */
	public int transposeDrum(int value) {
		//FIXME if mapping undefined return the default position
		int[] transposed = _mapping[value];
		
		return (transposed != null ? transposed[MAP_VALUE]: _defaultNote);
	}
	
	/**
	 * Returns the render type for value, or KIND_NOTE if not defined.
	 * @param value a note to be used for render type lookup
	 * @return the bitflagged render type for the note.
	 */
	public int getRenderType(int value) {
		int[] transposed = _mapping[value];
		
		return (transposed != null ? transposed[MAP_KIND]: KIND_NOTE);
	}
}
