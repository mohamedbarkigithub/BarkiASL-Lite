package com.mohamed.barki.asl.lite.resactivity;


import android.app.Activity;

@SuppressWarnings("SpellCheckingInspection")
public class ResActivityI extends Activity
{
	public static String[][] resList = new String[][]{
			//وسائل النقل
			{"i0", "13FDPhIrjFQPudyFmdPEZDZny5OEKeRSl", "ambulance", "سيارة الإسعاف"},
			{"i1", "url", "montgolfière1", "منطاد1"},
			{"i2", "url", "montgolfière2", "منطاد2"},
			{"i3", "url", "fusée", "صاروخ"},
			{"i4", "1JVo2_w2Iz8pFSMf_tWpEFHZUR4D5ouC4", "charrue", "محراث"},
			{"i5", "1IMEf9Y0kS6gNfWI0qbC20fffXxkTuArM", "engins et moyens de transport", "مركبات ووسائل النقل"},
			{"i6", "17zzCUkP5uvwVbz54qqcAZVNhy3er55Td", "feu avant", "مصباح أمامي"},
			{"i7", "1_Y_g5zMpYwIqHx7njIiHE-jgRsMNA9Pj", "moissonneuse", "حصادة"},
			{"i8", "111dy-jS7ZPGwBVBRZM7PQFpmkn3WnAxl", "pelleteuse", "حفارة"},
			{"i9", "13uHuonhcbVoCtldxuyxwOu_0moQHeuG2", "grue", "رافعة"},
			{"i10", "15k3ayosngSvKUqn3jfJ6JkEpSEO--Q4x", "signes lumineux", "اشارات ضوئية"},
			{"i11", "1bKgFBgqi8jQ4vQJqXERzRNgfuDLFUd0G", "tracteur", "جرار"},
			{"i12", "185ppY1YeQS1VFqwCQxYjBN8XnsflCnav", "bulldozer", "جرافة"},
			{"i13", "1IAmQSxgisHi_3PqyQLaqmqNzrjO969On", "braque", "زورق"},
			{"i14", "1jOFJm5-TRm4DqbNYmo8mgIUkv03WTC3f", "bateau1", "سفينة، باخرة1"},
			{"i15", "url", "bateau2", "سفينة، باخرة2"},
			{"i16", "1Pp3fMTXjAnE6a51JslBC_7YkpN2jQDe0", "taxi", "سيارة الأجرة"},
			{"i17", "1NRF_7pZ0hk6WHHhX0zFpWCfh3gxci8qA", "bus1", "حافلة1"},
			{"i18", "url", "bus2", "حافلة2"},
			{"i19", "1yVIpAwRQq0qmPZCKb1Jwqa9U2A1awJvM", "moto", "دراجة نارية"},
			{"i20", "1xPBkHAWd5LbTce7iCvlavE2ZT3peL0y2", "vélo1", "دراجة1"},
			{"i21", "url", "vélo2", "دراجة2"},
			{"i22", "1nFjkRRQ3a9FveOsUzxSqsV_I4Zc7xKHw", "métro", "مترو"},
			{"i23", "1lGeOhwGitIaNikP3mLwJiug2_9pXjsoP", "moyenne de transport", "وسائل النقل"},
			{"i24", "url", "gondole", "زورق البندقية"},
			{"i25", "1xKfcITWh9MWF7nHrjnaYbpEqvlRGVNbG", "capot de voiture", "غطاء المحرك"},
			{"i26", "1FOKPyhGW6TslTcvBJaUBvjW78TduAYx5", "clef de contact", "مفتاح التشغيل"},
			{"i27", "url", "téléphérique1", "مصعد هوائي1"},
			{"i28", "1GdPswHc_C6wqsFd-C4kh_ZXEMwFVAcnt", "voiture de pompiers", "سيارة الإطفاء"},
			{"i29", "1TfhjdzpnTOWVhk1vGvCAfm6rYwBcMlKx", "camion1", "شاحنة1"},
			{"i30", "url", "camion2", "شاحنة2"},
			{"i31", "1Qeq5aWe8SertEYZVF_a7-UpwHrDVjE9f", "volant", "عجلة القيادة"},
			{"i32", "1tETuPIBnQsYmHgBGYgPiy8HnLK81yCYt", "ascenseur", "مصعد"},
			{"i33", "1Yfv2victY4fHZpm1_8gTfL63tZVLLhy0", "tramway", "ترامواي"},
			{"i34", "1V6qhJprD9AOA61_AaYgWp4Q0u5de9vUn", "ceinture de sécurité", "حزام الأمن"},
			{"i35", "1tnHpptdagKrfA7mXq3NLyZlKr2sl4w8K", "avion1", "طائرة1"},
			{"i36", "url", "avion2", "طائرة2"},
			{"i37", "1zDNSGWIOTsDocbjH0bx3hzLZpaVF-JIz", "train1", "قطار1"},
			{"i38", "url", "train2", "قطار2"},
			{"i39", "1Qqh1sIxZP81j0beeiyklen4HJ4jeLjP_", "téléphérique2", "مصعد هوائي2"},
			{"i40", "1rJ0aKXBjkoNIJCcnaPbqmyPsimcRrsOk", "voiture1", "سيارة1"},
			{"i41", "url", "voiture2", "سيارة2"},
			{"i42", "1YnR6Pxz1FkA2dt70XfJZNjFEXvHXDuuA", "camion d'ordures", "شاحنة النفايات"},
			{"i43", "url", "sous-marin", "غواصة"},
			{"i44", "url", "trottinette", "سكوتر"},
			{"i45", "1QEhkGS9ytiJvF9bao7on8Hv33kcZcgCL", "hélicoptère", "طائرة مروحية"},
			{"i46", "1iqZzWqQIcdVD42OXxLZd-PLYnr-KdF_T", "indicateur", "المؤشر"},
			{"i47", "1itnfmfNIIBW6TM9iVVtibnUHIyVSwg9N", "pelle", "مجرفة"},
	};
}
