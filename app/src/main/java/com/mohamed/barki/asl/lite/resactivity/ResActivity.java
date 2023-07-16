package com.mohamed.barki.asl.resactivity;

import android.app.Activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResActivity extends Activity
{
	public static String[][][] list = new String[][][]{
			ResActivityA.resList,
			ResActivityB.resList,
			ResActivityC.resList,
			ResActivityD.resList,
			ResActivityE.resList,
			ResActivityF.resList,
			ResActivityG.resList,
			ResActivityH.resList,
			ResActivityI.resList,
			ResActivityJ.resList,
			ResActivityK.resList,
			ResActivityL.resList,
			ResActivityM.resList,
			ResActivityN.resList,
			ResActivityO.resList,
			ResActivityP.resList,
			ResActivityQ.resList,
			ResActivityR.resList,
			ResActivityS.resList,
			ResActivityT.resList,
			ResActivityU.resList,
			ResActivityV.resList,
			ResActivityW.resList,
			ResActivityX.resList,
			ResActivityY.resList,
			ResActivityZ.resList
	};

	public static String[][] resList(){
		List<String[]> lst = new ArrayList<>();
		for (String[][] strings : list) {
			lst.addAll(Arrays.asList(strings));
		}

		String[][] stockArr = new String[lst.size()][4];
		stockArr = lst.toArray(stockArr);

		return stockArr;
	}

	public static String[] nameDomaine = new String[]{
			"موضوع الفيديو"+
					"/ Thème de la vidéo",
			"الألوان"+
					"/ Couleurs",
			"الصفات، الحالات والأضداد"+
					"/ Comportements et Contraires",
			"الأشياء، الوسائل والمواد"+
					"/ Objets, Moyens et Matières",
			"مصطلحات سياسية وعسكرية"+
					"/ Terminologie Politique et Militaire",
			"الولايات الجزائرية"+
					"/ Wilayas Algériennes",
			"الأشكال"+
					"Formes",
			"الرياضة والتسلية"+
					"/ Sports et Loisirs",
			"الأعياد والمناسبات"+
					"/ Festivités et Évènements",
			"المركبات ووسائل النقل"+
					"/ Engins et Moyens de Transport",
			"المواد الغذائية"+
					"/ Produits Alimentaires",
			"الحيوانات والحشرات"+
					"/ Animaux et Insectes",
			"المباني والأماكن"+
					"/ Structures et Lieux",
			"المصطلحات الجغرافية ودول العالم"+
					"/ Terminologie géographique et Pays",
			"الكون والطبيعة"+
					"/ Espace et Nature",
			"الوظائف والمهن"+
					"/ Fonctions, Professions et Métiers",
			"الإدارة والوثائق الإدارية"+
					"/ Administration et Documents administratifs",
			"العدالة"+
					"/ Justice",
			"الصحة"+
					"/ Santé",
			"التواصل"+
					"/ Communication",
			"الكمية والقياس"+
					"Quantité et Mesure",
			"الأفعال وقواعد النحو"+
					"/ Verbes et Grammaire",
			"التربية والتعليم"+
					"/ Éducation et Enseignement",
			"الدين"+
					"/ Religion",
			"الزمن"+
					"/ Temps",
			"الملابس والحلي"+
					"/ Habillement et Bijoux",
			"المنزل"+
					"/ Maison",
			"الأسرة والعائلة"+
					"/ Famille",
			"جسم الإنسان"+
					"/ Corps humain",
			"الأرقام والأعداد"+
					"/ Chiffres et Nombres",
			"الأبجدية الإشارية"+
					"/ Dactylologie",
			"أخرى"+
					"/ Autre"
	};
	public static String[] nameDomaineAr = new String[]{
			"موضوع الفيديو",
			"الألوان",
			"الصفات، الحالات والأضداد",
			"الأشياء، الوسائل والمواد",
			"مصطلحات سياسية وعسكرية",
			"الولايات الجزائرية",
			"الأشكال",
			"الرياضة والتسلية",
			"الأعياد والمناسبات",
			"المركبات ووسائل النقل",
			"المواد الغذائية",
			"الحيوانات والحشرات",
			"المباني والأماكن",
			"المصطلحات الجغرافية ودول العالم",
			"الكون والطبيعة",
			"الوظائف والمهن",
			"الإدارة والوثائق الإدارية",
			"العدالة",
			"الصحة",
			"التواصل",
			"الكمية والقياس",
			"الأفعال وقواعد النحو",
			"التربية والتعليم",
			"الدين",
			"الزمن",
			"الملابس والحلي",
			"المنزل",
			"الأسرة والعائلة",
			"جسم الإنسان",
			"الأرقام والأعداد",
			"الأبجدية الإشارية",
			"أخرى"
	};
	public static String[] nameDomaineFr = new String[]{
			"Thème de la vidéo",
			"Couleurs",
			"Comportements et Contraires",
			"Objets, Moyens et Matières",
			"Terminologie Politique et Militaire",
			"Wilayas Algériennes",
			"Formes",
			"Sports et Loisirs",
			"Festivités et Évènements",
			"Engins et Moyens de Transport",
			"Produits Alimentaires",
			"Animaux et Insectes",
			"Structures et Lieux",
			"Terminologie géographique et Pays",
			"Espace et Nature",
			"Fonctions, Professions et Métiers",
			"Administration et Documents administratifs",
			"Justice",
			"Santé",
			"Communication",
			"Quantité et Mesure",
			"Verbes et Grammaire",
			"Éducation et Enseignement",
			"Religion",
			"Temps",
			"Habillement et Bijoux",
			"Maison",
			"Famille",
			"Corps humain",
			"Chiffres et Nombres",
			"Dactylologie",
			"Autre"
	};
}
