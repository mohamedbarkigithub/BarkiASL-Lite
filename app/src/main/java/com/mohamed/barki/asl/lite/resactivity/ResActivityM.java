package com.mohamed.barki.asl.lite.resactivity;

import android.app.Activity;

@SuppressWarnings("SpellCheckingInspection")
public class ResActivityM extends Activity
{
	public static String[][] resList = new String[][]{
			//جغرافيا
			{"m0", "1D9bXrYoJNiWi4Lx8FgqyCt9MxZ_Mp4oO", "saoudite", "السعودية"},
			{"m1", "1K-iL2k7ERkNNs28q-bK45JqHzRHdOZuO", "syrie", "سوريا"},
			{"m2", "1-qOWlIpMxs1OiukI35oMDNZ1ERFUpbMq", "afrique", "إفريقيا"},
			{"m3", "1GDlvcTWAMc-255g_zrJ_iS0sA-4XtMEH", "température", "درجة الحرارة"},
			{"m4", "10X8J5eEhr9oiQEgYVquO456KiAOfHRmh", "russie1", "روسيا1"},
			{"m5", "110ZYC87IpBoinva3zjWoroHAYaJCToEk", "côte", "ساحل"},
			{"m6", "11UOjSD3ku02cwO5w1IAF6IPMhXFjAvud", "turquie1", "تركيا1"},
			{"m7", "11YLzS91l5RkwTVLL-3_CQA79aSoMAR2J", "pay", "بلد"},
			{"m8", "11jiHAJLkjngOD1GTcdIKyyyVXKHDkuoC", "tunisie", "تونس"},
			{"m9", "11jyblGSE8V7YTLWYp6vKtYgYiAQRi2Gu", "afrique du sud", "جنوب إفريقيا"},
			{"m10", "1noHYjG8G_3jP43jmLdxLp6ierf0iC3O6", "sud", "جنوب"},
			{"m11", "1kQWxkoRgRD9HsTR5u44JlczaDHOUf6Av", "danemark", "الدانمارك"},
			{"m12", "19Zq_Op6nbZMwvUeaSoWtf-EGhiVbsqi7", "brésil1", "البرازيل1"},
			{"m13", "1J8UuJtQsL1DqrDzjaq2LSP2y3I50v40k", "grand-bretagne", "بريطانيا"},
			{"m14", "1tYrk-QtaQZlIoVYwJRYsgqUQxnYFi5-e", "belgique", "بلجيكا"},
			{"m15", "1n3LX1CmpDv42D-qZJwQnE2jbtADoxRL-", "jordanie", "الأردن"},
			{"m16", "12HTZ527paNd72VhNhIdnsV0Xo-RrqefE", "europe", "أوروبا"},
			{"m17", "1GLcvLmlcOxE79ikAilU1hv-0tu7KKzH8", "iran", "إيران"},
			{"m18", "14GgohghD3I2UxX_HSzw_suVC1xPyTvKH", "italie", "إيطاليا"},
			{"m19", "1G7fusU-de3O-ehhQ9iGWyaPpKO6Jgs_F", "allemagne", "ألمانيا"},
			{"m20", "1wTjfAHCKNgPzA5Df3S069aZxs5o_uRwh", "amérique du sud", "أمريكا الجنوبية"},
			{"m21", "16smkUBiFIiWgnOJ8vzqp0az9vc2f54hc", "amérique du nord", "أمريكا الشمالية"},
			{"m22", "1T-3ST4MtBegbcy8_2YmiB7tVzwuUXMJu", "asie", "آسيا"},
			{"m23", "1RUJG_tO0UnV8ExiKmIYPxSaq4qCV8bZI", "carte de géographie", "خريطة"},
			{"m24", "1nE5YLea_UZB9guatMRlLK3zq5iHELZjC", "drapeau", "علم"},
			{"m25", "13-6QHwT_RLxMRh0dUK11ro0UZIP3BQ2c", "espagne", "إسبانيا"},
			{"m26", "1iHU5Rm-rQh6X2kSpyWN55PixgKW_SVs-", "australie1", "أستراليا1"},
			{"m27", "10F0WSXrGOnSa1A8MtLbKoAwiZZEpCXGn", "algérie1", "الجزائر1"},
			{"m28", "1OEXcdtguQFz7ZaMhWs5P9pKIFGNgoTWo", "direction", "إتجاهات"},
			{"m29", "16Cazp1AP4kAaj4uRmaSUt4b-STpAFqof", "argentine", "الأرجنتين"},
			{"m30", "1HOEt57sdTZx4c4-U4gD5s2V6c-dloSAU", "est", "شرق"},
			{"m31", "1XXVj2oo8tBGVNFFyrk2ulqtMIfrZ43yn", "suisse", "سويسرا"},
			{"m32", "1dhyN-4k5K6hyBekmVCTb4njL3C_KRx0Z", "suède", "السويد"},
			{"m33", "1028CXDXUZSGeslUMmLVrwPkJZu4G7Ooo", "chine1", "الصين1"},
			{"m34", "109wxT1WlDM21KW3J-5UhM8bxRaVuNofn", "sahara occidental", "الصحراء الغربية"},
			{"m35", "10CplSqOQLtm1TXvi2S7MWahaEkiHNEF-", "nord", "شمال"},
			{"m36", "10GrETtcwo4tZK23xxtnB7pYnI5cuCXNM", "irak", "العراق"},
			{"m37", "10H_ZKybXIPdAMSrJvbTcaAAvi2v0tplQ", "monde", "عالم"},
			{"m38", "10LqrkDWphkrcam6uSZLJzicyg8O9eIU-", "climat", "مناخ"},
			{"m39", "10WQqjqYmExDRbhhimraKkXpytjeD7cZI", "palestine", "فلسطين"},
			{"m40", "10Xh1wHTn1XnnjXAaoh4boPc8kikJ3mDx", "france", "فرنسا"},
			{"m41", "10_yFxmb6uBnVquc5gWhAEIKdqgeTCbPD", "ouest", "غرب"},
			{"m42", "10e8ZvqLCgZdn8u-0TH7MsDMbXfynIU3F", "canada1", "كندا1"},
			{"m43", "10eLb-O_lj-2VS9ABcWNuB7po1Uui_YIV", "continent", "قارة"},
			{"m44", "10iOUYDz1VPt-OeKJsi0Sb-GpZbzQhkGu", "finlande", "فنلندا"},
			{"m45", "10k-XF1lGoeIskT-0Q9Dc1xGLX0ZXTuHZ", "liban", "لبنان"},
			{"m46", "10p1a1WnvLQgU0YHvPkU7voIUsLo-TN0H", "koweït", "الكويت"},
			{"m47", "10vXHts9N64mPfE2sajPdq9RS3MTQI3CA", "cuba", "كوبا"},
			{"m48", "10wXs7wD4XdqNUV2IF6BUEFW1F4VIZj5a", "égypte", "مصر"},
			{"m49", "10xbQU2_n4Qa6stZLQ17OZy7_6Wl5NrEM", "malaisie", "ماليزيا"},
			{"m50", "112rjHg2oejTrVGOCxk1C1W73hWWAieoG", "libye", "ليبيا"},
			{"m51", "115DmJPmJ-_s9h-bOjEQGQvBMC5HYFGdY", "mexique1", "المكسيك1"},
			{"m52", "1179y6QVafUSJ5OFtbJbMIiB1ygbbLfvs", "maroc", "المغرب"},
			{"m53", "1186Hx-K4UEH8uQ-iZ3GMofmJv0GiXRJC", "terminologie géographique", "مصطلحات جغرافية"},
			{"m54", "11GTDoVpoVPh6HitRkLicZInK5F6YnAzd", "états-unis d'amérique1", "الولايات المتحدة الأمريكية1"},
			{"m55", "11IhuT-ZGGZDz3J83tuPRdqxrRPZTaz8A", "inde", "الهند"},
			{"m56", "11JZwK_IZAM1dSQSs7bo6EgOuSnns45O4", "mauritanie", "موريطانيا"},
			{"m57", "11Jow1IGsIP0HasKuapfcCB9wzOEVlDTF", "grèce", "اليونان"},
			{"m58", "11KuRkyV1Te3zR-i-Bqpah9QwAUCDvhJT", "yémen", "اليمن"},
			{"m59", "11MvD40-TWbBwsddPhL6UJ2ofAUBe20Np", "japon1", "اليابان1"},
			{"m60", "1gtT7lFgIb2bqb2pGn37MxG3iLJnlBGQ0", "antarctique", "القطب الجنوبي"},
			{"m61", "1aeCWXnnnet9Qul3zXopUOHWhgNg1_6hV", "océanie", "أوقيانوسيا"},
			{"m62", "1ufeV5Mq1ubbJLYZmg3W8M654wcNOoStF", "océan arctique", "المحيط المتجمد الشمالي"},
			{"m63", "1_Q0qkUcRlgSRkvXrNUEQ2ACpgaVTrye0", "océan atlantique", "المحيط الأطلسي"},
			{"m64", "1CiBDJC7NIWw_lBzExssQupjmN1oC5w_h", "océan antarctique", "محيط القطب الجنوبي"},
			{"m65", "1dlQ9qB3HdGEBftaj68i3wcZJRSox_mZh", "océan indien", "المحيط الهندي"},
			{"m66", "1CXadMdypUaslbuItUydxFqK8JR-16dI1", "océan pacifique", "المحيط الهادي"},
			{"m67", "1eknBtuXu2LZOiRmWrBRgj7K8GiBxqJIy", "alpes (montagne)", "جبال الألب"},
			{"m68", "1425mRjLu7Alw-VS1tD_CPigGlC0dKmOy", "jura (montagne)", "جبل جورا"},
			{"m69", "1TVVbSIbYGp-qy1eNDtvwQ9Tc_E83uXpw", "pyrénées (montagne)", "جبال البرانس"},
			{"m71", "1pGwxXDYYEoFAk6tPEttgGfnEjTQSZ_Wr", "Vosges (montagne)", "جبال الفوج"},
			{"m72", "url", "météo", "طقس"},
			{"m73", "url", "canada2", "كندا2"},
			{"m74", "url", "mexique2", "المكسيك2"},
			{"m75", "url", "thaïlande", "تيلاندا"},
			{"m76", "url", "australie2", "أستراليا2"},
			{"m77", "url", "norèvge", "النرويج"},
			{"m78", "url", "algérie2", "الجزائر2"},
			{"m79", "url", "brésil2", "البرازيل2"},
			{"m80", "url", "chine2", "الصين2"},
			{"m81", "url", "russie2", "روسيا2"},
			{"m82", "url", "venezuela", "فنزويلا"},
			{"m83", "url", "japon2", "اليابان2"},
			{"m84", "url", "turquie2", "تركيا2"},
			{"m85", "url", "états-unis d'amérique2", "الولايات المتحدة الأمريكية2"},
	};
}
