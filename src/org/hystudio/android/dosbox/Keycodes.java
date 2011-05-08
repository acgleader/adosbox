package org.hystudio.android.dosbox;

import java.lang.String;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.reflect.Field;

class SDL_1_2_Keycodes {

	public static final int SDLK_UNKNOWN = 0;
	public static final int SDLK_BACKSPACE = 8;
	public static final int SDLK_TAB = 9;
	public static final int SDLK_CLEAR = 12;
	public static final int SDLK_RETURN = 13;
	public static final int SDLK_PAUSE = 19;
	public static final int SDLK_ESCAPE = 27;
	public static final int SDLK_SPACE = 32;
	public static final int SDLK_EXCLAIM = 33;
	public static final int SDLK_QUOTEDBL = 34;
	public static final int SDLK_HASH = 35;
	public static final int SDLK_DOLLAR = 36;
	public static final int SDLK_AMPERSAND = 38;
	public static final int SDLK_QUOTE = 39;
	public static final int SDLK_LEFTPAREN = 40;
	public static final int SDLK_RIGHTPAREN = 41;
	public static final int SDLK_ASTERISK = 42;
	public static final int SDLK_PLUS = 43;
	public static final int SDLK_COMMA = 44;
	public static final int SDLK_MINUS = 45;
	public static final int SDLK_PERIOD = 46;
	public static final int SDLK_SLASH = 47;
	public static final int SDLK_0 = 48;
	public static final int SDLK_1 = 49;
	public static final int SDLK_2 = 50;
	public static final int SDLK_3 = 51;
	public static final int SDLK_4 = 52;
	public static final int SDLK_5 = 53;
	public static final int SDLK_6 = 54;
	public static final int SDLK_7 = 55;
	public static final int SDLK_8 = 56;
	public static final int SDLK_9 = 57;
	public static final int SDLK_COLON = 58;
	public static final int SDLK_SEMICOLON = 59;
	public static final int SDLK_LESS = 60;
	public static final int SDLK_EQUALS = 61;
	public static final int SDLK_GREATER = 62;
	public static final int SDLK_QUESTION = 63;
	public static final int SDLK_AT = 64;
	public static final int SDLK_LEFTBRACKET = 91;
	public static final int SDLK_BACKSLASH = 92;
	public static final int SDLK_RIGHTBRACKET = 93;
	public static final int SDLK_CARET = 94;
	public static final int SDLK_UNDERSCORE = 95;
	public static final int SDLK_BACKQUOTE = 96;
	public static final int SDLK_a = 97;
	public static final int SDLK_b = 98;
	public static final int SDLK_c = 99;
	public static final int SDLK_d = 100;
	public static final int SDLK_e = 101;
	public static final int SDLK_f = 102;
	public static final int SDLK_g = 103;
	public static final int SDLK_h = 104;
	public static final int SDLK_i = 105;
	public static final int SDLK_j = 106;
	public static final int SDLK_k = 107;
	public static final int SDLK_l = 108;
	public static final int SDLK_m = 109;
	public static final int SDLK_n = 110;
	public static final int SDLK_o = 111;
	public static final int SDLK_p = 112;
	public static final int SDLK_q = 113;
	public static final int SDLK_r = 114;
	public static final int SDLK_s = 115;
	public static final int SDLK_t = 116;
	public static final int SDLK_u = 117;
	public static final int SDLK_v = 118;
	public static final int SDLK_w = 119;
	public static final int SDLK_x = 120;
	public static final int SDLK_y = 121;
	public static final int SDLK_z = 122;
	public static final int SDLK_DELETE = 127;
	public static final int SDLK_WORLD_0 = 160;
	public static final int SDLK_WORLD_1 = 161;
	public static final int SDLK_WORLD_2 = 162;
	public static final int SDLK_WORLD_3 = 163;
	public static final int SDLK_WORLD_4 = 164;
	public static final int SDLK_WORLD_5 = 165;
	public static final int SDLK_WORLD_6 = 166;
	public static final int SDLK_WORLD_7 = 167;
	public static final int SDLK_WORLD_8 = 168;
	public static final int SDLK_WORLD_9 = 169;
	public static final int SDLK_WORLD_10 = 170;
	public static final int SDLK_WORLD_11 = 171;
	public static final int SDLK_WORLD_12 = 172;
	public static final int SDLK_WORLD_13 = 173;
	public static final int SDLK_WORLD_14 = 174;
	public static final int SDLK_WORLD_15 = 175;
	public static final int SDLK_WORLD_16 = 176;
	public static final int SDLK_WORLD_17 = 177;
	public static final int SDLK_WORLD_18 = 178;
	public static final int SDLK_WORLD_19 = 179;
	public static final int SDLK_WORLD_20 = 180;
	public static final int SDLK_WORLD_21 = 181;
	public static final int SDLK_WORLD_22 = 182;
	public static final int SDLK_WORLD_23 = 183;
	public static final int SDLK_WORLD_24 = 184;
	public static final int SDLK_WORLD_25 = 185;
	public static final int SDLK_WORLD_26 = 186;
	public static final int SDLK_WORLD_27 = 187;
	public static final int SDLK_WORLD_28 = 188;
	public static final int SDLK_WORLD_29 = 189;
	public static final int SDLK_WORLD_30 = 190;
	public static final int SDLK_WORLD_31 = 191;
	public static final int SDLK_WORLD_32 = 192;
	public static final int SDLK_WORLD_33 = 193;
	public static final int SDLK_WORLD_34 = 194;
	public static final int SDLK_WORLD_35 = 195;
	public static final int SDLK_WORLD_36 = 196;
	public static final int SDLK_WORLD_37 = 197;
	public static final int SDLK_WORLD_38 = 198;
	public static final int SDLK_WORLD_39 = 199;
	public static final int SDLK_WORLD_40 = 200;
	public static final int SDLK_WORLD_41 = 201;
	public static final int SDLK_WORLD_42 = 202;
	public static final int SDLK_WORLD_43 = 203;
	public static final int SDLK_WORLD_44 = 204;
	public static final int SDLK_WORLD_45 = 205;
	public static final int SDLK_WORLD_46 = 206;
	public static final int SDLK_WORLD_47 = 207;
	public static final int SDLK_WORLD_48 = 208;
	public static final int SDLK_WORLD_49 = 209;
	public static final int SDLK_WORLD_50 = 210;
	public static final int SDLK_WORLD_51 = 211;
	public static final int SDLK_WORLD_52 = 212;
	public static final int SDLK_WORLD_53 = 213;
	public static final int SDLK_WORLD_54 = 214;
	public static final int SDLK_WORLD_55 = 215;
	public static final int SDLK_WORLD_56 = 216;
	public static final int SDLK_WORLD_57 = 217;
	public static final int SDLK_WORLD_58 = 218;
	public static final int SDLK_WORLD_59 = 219;
	public static final int SDLK_WORLD_60 = 220;
	public static final int SDLK_WORLD_61 = 221;
	public static final int SDLK_WORLD_62 = 222;
	public static final int SDLK_WORLD_63 = 223;
	public static final int SDLK_WORLD_64 = 224;
	public static final int SDLK_WORLD_65 = 225;
	public static final int SDLK_WORLD_66 = 226;
	public static final int SDLK_WORLD_67 = 227;
	public static final int SDLK_WORLD_68 = 228;
	public static final int SDLK_WORLD_69 = 229;
	public static final int SDLK_WORLD_70 = 230;
	public static final int SDLK_WORLD_71 = 231;
	public static final int SDLK_WORLD_72 = 232;
	public static final int SDLK_WORLD_73 = 233;
	public static final int SDLK_WORLD_74 = 234;
	public static final int SDLK_WORLD_75 = 235;
	public static final int SDLK_WORLD_76 = 236;
	public static final int SDLK_WORLD_77 = 237;
	public static final int SDLK_WORLD_78 = 238;
	public static final int SDLK_WORLD_79 = 239;
	public static final int SDLK_WORLD_80 = 240;
	public static final int SDLK_WORLD_81 = 241;
	public static final int SDLK_WORLD_82 = 242;
	public static final int SDLK_WORLD_83 = 243;
	public static final int SDLK_WORLD_84 = 244;
	public static final int SDLK_WORLD_85 = 245;
	public static final int SDLK_WORLD_86 = 246;
	public static final int SDLK_WORLD_87 = 247;
	public static final int SDLK_WORLD_88 = 248;
	public static final int SDLK_WORLD_89 = 249;
	public static final int SDLK_WORLD_90 = 250;
	public static final int SDLK_WORLD_91 = 251;
	public static final int SDLK_WORLD_92 = 252;
	public static final int SDLK_WORLD_93 = 253;
	public static final int SDLK_WORLD_94 = 254;
	public static final int SDLK_WORLD_95 = 255;
	public static final int SDLK_KP0 = 256;
	public static final int SDLK_KP1 = 257;
	public static final int SDLK_KP2 = 258;
	public static final int SDLK_KP3 = 259;
	public static final int SDLK_KP4 = 260;
	public static final int SDLK_KP5 = 261;
	public static final int SDLK_KP6 = 262;
	public static final int SDLK_KP7 = 263;
	public static final int SDLK_KP8 = 264;
	public static final int SDLK_KP9 = 265;
	public static final int SDLK_KP_PERIOD = 266;
	public static final int SDLK_KP_DIVIDE = 267;
	public static final int SDLK_KP_MULTIPLY = 268;
	public static final int SDLK_KP_MINUS = 269;
	public static final int SDLK_KP_PLUS = 270;
	public static final int SDLK_KP_ENTER = 271;
	public static final int SDLK_KP_EQUALS = 272;
	public static final int SDLK_UP = 273;
	public static final int SDLK_DOWN = 274;
	public static final int SDLK_RIGHT = 275;
	public static final int SDLK_LEFT = 276;
	public static final int SDLK_INSERT = 277;
	public static final int SDLK_HOME = 278;
	public static final int SDLK_END = 279;
	public static final int SDLK_PAGEUP = 280;
	public static final int SDLK_PAGEDOWN = 281;
	public static final int SDLK_F1 = 282;
	public static final int SDLK_F2 = 283;
	public static final int SDLK_F3 = 284;
	public static final int SDLK_F4 = 285;
	public static final int SDLK_F5 = 286;
	public static final int SDLK_F6 = 287;
	public static final int SDLK_F7 = 288;
	public static final int SDLK_F8 = 289;
	public static final int SDLK_F9 = 290;
	public static final int SDLK_F10 = 291;
	public static final int SDLK_F11 = 292;
	public static final int SDLK_F12 = 293;
	public static final int SDLK_F13 = 294;
	public static final int SDLK_F14 = 295;
	public static final int SDLK_F15 = 296;
	public static final int SDLK_NUMLOCK = 300;
	public static final int SDLK_CAPSLOCK = 301;
	public static final int SDLK_SCROLLOCK = 302;
	public static final int SDLK_RSHIFT = 303;
	public static final int SDLK_LSHIFT = 304;
	public static final int SDLK_RCTRL = 305;
	public static final int SDLK_LCTRL = 306;
	public static final int SDLK_RALT = 307;
	public static final int SDLK_LALT = 308;
	public static final int SDLK_RMETA = 309;
	public static final int SDLK_LMETA = 310;
	public static final int SDLK_LSUPER = 311;
	public static final int SDLK_RSUPER = 312;
	public static final int SDLK_MODE = 313;
	public static final int SDLK_COMPOSE = 314;
	public static final int SDLK_HELP = 315;
	public static final int SDLK_PRINT = 316;
	public static final int SDLK_SYSREQ = 317;
	public static final int SDLK_BREAK = 318;
	public static final int SDLK_MENU = 319;
	public static final int SDLK_POWER = 320;
	public static final int SDLK_EURO = 321;
	public static final int SDLK_UNDO = 322;

}

// Autogenerated by hand with a command:
// grep 'SDL_SCANCODE_' SDL_scancode.h | sed
// 's/SDL_SCANCODE_\([a-zA-Z0-9_]\+\).*[=] \([0-9]\+\).*/public static final int
// SDLK_\1 = \2;/' >> Keycodes.java
class SDL_1_3_Keycodes {

	public static final int SDLK_UNKNOWN = 0;
	public static final int SDLK_A = 4;
	public static final int SDLK_B = 5;
	public static final int SDLK_C = 6;
	public static final int SDLK_D = 7;
	public static final int SDLK_E = 8;
	public static final int SDLK_F = 9;
	public static final int SDLK_G = 10;
	public static final int SDLK_H = 11;
	public static final int SDLK_I = 12;
	public static final int SDLK_J = 13;
	public static final int SDLK_K = 14;
	public static final int SDLK_L = 15;
	public static final int SDLK_M = 16;
	public static final int SDLK_N = 17;
	public static final int SDLK_O = 18;
	public static final int SDLK_P = 19;
	public static final int SDLK_Q = 20;
	public static final int SDLK_R = 21;
	public static final int SDLK_S = 22;
	public static final int SDLK_T = 23;
	public static final int SDLK_U = 24;
	public static final int SDLK_V = 25;
	public static final int SDLK_W = 26;
	public static final int SDLK_X = 27;
	public static final int SDLK_Y = 28;
	public static final int SDLK_Z = 29;
	public static final int SDLK_1 = 30;
	public static final int SDLK_2 = 31;
	public static final int SDLK_3 = 32;
	public static final int SDLK_4 = 33;
	public static final int SDLK_5 = 34;
	public static final int SDLK_6 = 35;
	public static final int SDLK_7 = 36;
	public static final int SDLK_8 = 37;
	public static final int SDLK_9 = 38;
	public static final int SDLK_0 = 39;
	public static final int SDLK_RETURN = 40;
	public static final int SDLK_ESCAPE = 41;
	public static final int SDLK_BACKSPACE = 42;
	public static final int SDLK_TAB = 43;
	public static final int SDLK_SPACE = 44;
	public static final int SDLK_MINUS = 45;
	public static final int SDLK_EQUALS = 46;
	public static final int SDLK_LEFTBRACKET = 47;
	public static final int SDLK_RIGHTBRACKET = 48;
	public static final int SDLK_BACKSLASH = 49;
	public static final int SDLK_NONUSHASH = 50;
	public static final int SDLK_SEMICOLON = 51;
	public static final int SDLK_APOSTROPHE = 52;
	public static final int SDLK_GRAVE = 53;
	public static final int SDLK_COMMA = 54;
	public static final int SDLK_PERIOD = 55;
	public static final int SDLK_SLASH = 56;
	public static final int SDLK_CAPSLOCK = 57;
	public static final int SDLK_F1 = 58;
	public static final int SDLK_F2 = 59;
	public static final int SDLK_F3 = 60;
	public static final int SDLK_F4 = 61;
	public static final int SDLK_F5 = 62;
	public static final int SDLK_F6 = 63;
	public static final int SDLK_F7 = 64;
	public static final int SDLK_F8 = 65;
	public static final int SDLK_F9 = 66;
	public static final int SDLK_F10 = 67;
	public static final int SDLK_F11 = 68;
	public static final int SDLK_F12 = 69;
	public static final int SDLK_PRINTSCREEN = 70;
	public static final int SDLK_SCROLLLOCK = 71;
	public static final int SDLK_PAUSE = 72;
	public static final int SDLK_INSERT = 73;
	public static final int SDLK_HOME = 74;
	public static final int SDLK_PAGEUP = 75;
	public static final int SDLK_DELETE = 76;
	public static final int SDLK_END = 77;
	public static final int SDLK_PAGEDOWN = 78;
	public static final int SDLK_RIGHT = 79;
	public static final int SDLK_LEFT = 80;
	public static final int SDLK_DOWN = 81;
	public static final int SDLK_UP = 82;
	public static final int SDLK_NUMLOCKCLEAR = 83;
	public static final int SDLK_KP_DIVIDE = 84;
	public static final int SDLK_KP_MULTIPLY = 85;
	public static final int SDLK_KP_MINUS = 86;
	public static final int SDLK_KP_PLUS = 87;
	public static final int SDLK_KP_ENTER = 88;
	public static final int SDLK_KP_1 = 89;
	public static final int SDLK_KP_2 = 90;
	public static final int SDLK_KP_3 = 91;
	public static final int SDLK_KP_4 = 92;
	public static final int SDLK_KP_5 = 93;
	public static final int SDLK_KP_6 = 94;
	public static final int SDLK_KP_7 = 95;
	public static final int SDLK_KP_8 = 96;
	public static final int SDLK_KP_9 = 97;
	public static final int SDLK_KP_0 = 98;
	public static final int SDLK_KP_PERIOD = 99;
	public static final int SDLK_NONUSBACKSLASH = 100;
	public static final int SDLK_APPLICATION = 101;
	public static final int SDLK_POWER = 102;
	public static final int SDLK_KP_EQUALS = 103;
	public static final int SDLK_F13 = 104;
	public static final int SDLK_F14 = 105;
	public static final int SDLK_F15 = 106;
	public static final int SDLK_F16 = 107;
	public static final int SDLK_F17 = 108;
	public static final int SDLK_F18 = 109;
	public static final int SDLK_F19 = 110;
	public static final int SDLK_F20 = 111;
	public static final int SDLK_F21 = 112;
	public static final int SDLK_F22 = 113;
	public static final int SDLK_F23 = 114;
	public static final int SDLK_F24 = 115;
	public static final int SDLK_EXECUTE = 116;
	public static final int SDLK_HELP = 117;
	public static final int SDLK_MENU = 118;
	public static final int SDLK_SELECT = 119;
	public static final int SDLK_STOP = 120;
	public static final int SDLK_AGAIN = 121;
	public static final int SDLK_UNDO = 122;
	public static final int SDLK_CUT = 123;
	public static final int SDLK_COPY = 124;
	public static final int SDLK_PASTE = 125;
	public static final int SDLK_FIND = 126;
	public static final int SDLK_MUTE = 127;
	public static final int SDLK_VOLUMEUP = 128;
	public static final int SDLK_VOLUMEDOWN = 129;
	public static final int SDLK_KP_COMMA = 133;
	public static final int SDLK_KP_EQUALSAS400 = 134;
	public static final int SDLK_INTERNATIONAL1 = 135;
	public static final int SDLK_INTERNATIONAL2 = 136;
	public static final int SDLK_INTERNATIONAL3 = 137;
	public static final int SDLK_INTERNATIONAL4 = 138;
	public static final int SDLK_INTERNATIONAL5 = 139;
	public static final int SDLK_INTERNATIONAL6 = 140;
	public static final int SDLK_INTERNATIONAL7 = 141;
	public static final int SDLK_INTERNATIONAL8 = 142;
	public static final int SDLK_INTERNATIONAL9 = 143;
	public static final int SDLK_LANG1 = 144;
	public static final int SDLK_LANG2 = 145;
	public static final int SDLK_LANG3 = 146;
	public static final int SDLK_LANG4 = 147;
	public static final int SDLK_LANG5 = 148;
	public static final int SDLK_LANG6 = 149;
	public static final int SDLK_LANG7 = 150;
	public static final int SDLK_LANG8 = 151;
	public static final int SDLK_LANG9 = 152;
	public static final int SDLK_ALTERASE = 153;
	public static final int SDLK_SYSREQ = 154;
	public static final int SDLK_CANCEL = 155;
	public static final int SDLK_CLEAR = 156;
	public static final int SDLK_PRIOR = 157;
	public static final int SDLK_RETURN2 = 158;
	public static final int SDLK_SEPARATOR = 159;
	public static final int SDLK_OUT = 160;
	public static final int SDLK_OPER = 161;
	public static final int SDLK_CLEARAGAIN = 162;
	public static final int SDLK_CRSEL = 163;
	public static final int SDLK_EXSEL = 164;
	public static final int SDLK_KP_00 = 176;
	public static final int SDLK_KP_000 = 177;
	public static final int SDLK_THOUSANDSSEPARATOR = 178;
	public static final int SDLK_DECIMALSEPARATOR = 179;
	public static final int SDLK_CURRENCYUNIT = 180;
	public static final int SDLK_CURRENCYSUBUNIT = 181;
	public static final int SDLK_KP_LEFTPAREN = 182;
	public static final int SDLK_KP_RIGHTPAREN = 183;
	public static final int SDLK_KP_LEFTBRACE = 184;
	public static final int SDLK_KP_RIGHTBRACE = 185;
	public static final int SDLK_KP_TAB = 186;
	public static final int SDLK_KP_BACKSPACE = 187;
	public static final int SDLK_KP_A = 188;
	public static final int SDLK_KP_B = 189;
	public static final int SDLK_KP_C = 190;
	public static final int SDLK_KP_D = 191;
	public static final int SDLK_KP_E = 192;
	public static final int SDLK_KP_F = 193;
	public static final int SDLK_KP_XOR = 194;
	public static final int SDLK_KP_POWER = 195;
	public static final int SDLK_KP_PERCENT = 196;
	public static final int SDLK_KP_LESS = 197;
	public static final int SDLK_KP_GREATER = 198;
	public static final int SDLK_KP_AMPERSAND = 199;
	public static final int SDLK_KP_DBLAMPERSAND = 200;
	public static final int SDLK_KP_VERTICALBAR = 201;
	public static final int SDLK_KP_DBLVERTICALBAR = 202;
	public static final int SDLK_KP_COLON = 203;
	public static final int SDLK_KP_HASH = 204;
	public static final int SDLK_KP_SPACE = 205;
	public static final int SDLK_KP_AT = 206;
	public static final int SDLK_KP_EXCLAM = 207;
	public static final int SDLK_KP_MEMSTORE = 208;
	public static final int SDLK_KP_MEMRECALL = 209;
	public static final int SDLK_KP_MEMCLEAR = 210;
	public static final int SDLK_KP_MEMADD = 211;
	public static final int SDLK_KP_MEMSUBTRACT = 212;
	public static final int SDLK_KP_MEMMULTIPLY = 213;
	public static final int SDLK_KP_MEMDIVIDE = 214;
	public static final int SDLK_KP_PLUSMINUS = 215;
	public static final int SDLK_KP_CLEAR = 216;
	public static final int SDLK_KP_CLEARENTRY = 217;
	public static final int SDLK_KP_BINARY = 218;
	public static final int SDLK_KP_OCTAL = 219;
	public static final int SDLK_KP_DECIMAL = 220;
	public static final int SDLK_KP_HEXADECIMAL = 221;
	public static final int SDLK_LCTRL = 224;
	public static final int SDLK_LSHIFT = 225;
	public static final int SDLK_LALT = 226;
	public static final int SDLK_LGUI = 227;
	public static final int SDLK_RCTRL = 228;
	public static final int SDLK_RSHIFT = 229;
	public static final int SDLK_RALT = 230;
	public static final int SDLK_RGUI = 231;
	public static final int SDLK_MODE = 257;
	public static final int SDLK_AUDIONEXT = 258;
	public static final int SDLK_AUDIOPREV = 259;
	public static final int SDLK_AUDIOSTOP = 260;
	public static final int SDLK_AUDIOPLAY = 261;
	public static final int SDLK_AUDIOMUTE = 262;
	public static final int SDLK_MEDIASELECT = 263;
	public static final int SDLK_WWW = 264;
	public static final int SDLK_MAIL = 265;
	public static final int SDLK_CALCULATOR = 266;
	public static final int SDLK_COMPUTER = 267;
	public static final int SDLK_AC_SEARCH = 268;
	public static final int SDLK_AC_HOME = 269;
	public static final int SDLK_AC_BACK = 270;
	public static final int SDLK_AC_FORWARD = 271;
	public static final int SDLK_AC_STOP = 272;
	public static final int SDLK_AC_REFRESH = 273;
	public static final int SDLK_AC_BOOKMARKS = 274;
	public static final int SDLK_BRIGHTNESSDOWN = 275;
	public static final int SDLK_BRIGHTNESSUP = 276;
	public static final int SDLK_DISPLAYSWITCH = 277;
	public static final int SDLK_KBDILLUMTOGGLE = 278;
	public static final int SDLK_KBDILLUMDOWN = 279;
	public static final int SDLK_KBDILLUMUP = 280;
	public static final int SDLK_EJECT = 281;
	public static final int SDLK_SLEEP = 282;

}

class SDL_Keys {
	public static String[] names = null;
	public static Integer[] values = null;

	public static String[] namesSorted = null;
	public static Integer[] namesSortedIdx = null;
	public static Integer[] namesSortedBackIdx = null;

	static final int JAVA_KEYCODE_LAST = 110; // Android 2.3 added several new
												// gaming keys, it ends up at
												// keycode 110 currently - plz
												// keep in sync with
												// javakeycodes.h

	static {
		ArrayList<String> Names = new ArrayList<String>();
		ArrayList<Integer> Values = new ArrayList<Integer>();
		Field[] fields = SDL_1_2_Keycodes.class.getDeclaredFields();
		if (Globals.Using_SDL_1_3) {
			fields = SDL_1_3_Keycodes.class.getDeclaredFields();
		}

		try {
			for (Field f : fields) {
				Values.add(f.getInt(null));
				Names.add(f.getName().substring(5).toUpperCase());
			}
		} catch (IllegalAccessException e) {
		}
		;

		// Sort by value
		for (int i = 0; i < Values.size(); i++) {
			for (int j = i; j < Values.size(); j++) {
				if (Values.get(i) > Values.get(j)) {
					int x = Values.get(i);
					Values.set(i, Values.get(j));
					Values.set(j, x);
					String s = Names.get(i);
					Names.set(i, Names.get(j));
					Names.set(j, s);
				}
			}
		}

		names = Names.toArray(new String[0]);
		values = Values.toArray(new Integer[0]);
		namesSorted = Names.toArray(new String[0]);
		namesSortedIdx = new Integer[values.length];
		namesSortedBackIdx = new Integer[values.length];
		Arrays.sort(namesSorted);
		for (int i = 0; i < namesSorted.length; i++) {
			for (int j = 0; j < namesSorted.length; j++) {
				if (namesSorted[i].equals(names[j])) {
					namesSortedIdx[i] = j;
					namesSortedBackIdx[j] = i;
					break;
				}
			}
		}
	}
}
