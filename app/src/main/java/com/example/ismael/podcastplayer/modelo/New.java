package com.example.ismael.podcastplayer.modelo;

/**
 * Clase que modela a varios RSS de noticias. Mirar final del fichero para su estructura.
 * Created by Ismael on 11/02/2018.
 */
public class New extends ElementoGenerico {

    private String titulo;
    private String url;
    private String pubDate;

}

/*
TODO: Cryptopanic: https://cryptopanic.com/about/api/

<item>
<title>Study: Cryptocurrency Market Operates in Landscape of Heightened Risk</title>
<link>https://cryptopanic.com/news/1051685/Study-Cryptocurrency-Market-Operates-in-Landscape-of-Heightened-Risk</link>
<pubDate>Sun, 11 Feb 2018 21:01:38 +0000</pubDate>
<guid>https://cryptopanic.com/news/1051685/Study-Cryptocurrency-Market-Operates-in-Landscape-of-Heightened-Risk</guid>
</item>

TODO: InsideBitcoins: https://insidebitcoins.com/feed

<item>
<title>Russian Restaurant Chain Launches ICO-Themed Menu Complete With Chinese Ban</title>
<description><![CDATA[ICOs continue to infiltrate mainstream culture, with a major Russian restaurant chain releasing a crypto-themed menu.]]></description>
<dc:creator>CoinTelegraph by William Suberg</dc:creator>
<comments>https://insidebitcoins.com/news/russian-restaurant-chain-launches-ico-themed-menu-complete-with-chinese-ban/76393#respond</comments>
<link>https://insidebitcoins.com/news/russian-restaurant-chain-launches-ico-themed-menu-complete-with-chinese-ban/76393</link>
<media:content url="https://insidebitcoins.com/wp-content/uploads/2017/10/logo-87.png" width="137" height="90" medium="image"/>
<pubDate>Sun, 11 Feb 2018 18:59:55 +0000</pubDate>
<category><![CDATA[ Uncategorized ]]></category>
</item>

 */