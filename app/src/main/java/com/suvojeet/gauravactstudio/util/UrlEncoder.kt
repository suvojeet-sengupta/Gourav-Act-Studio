package com.suvojeet.gauravactstudio.util

import java.net.URLEncoder
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

fun String.encodeURL(): String = URLEncoder.encode(this, StandardCharsets.UTF_8.toString())
fun String.decodeURL(): String = URLDecoder.decode(this, StandardCharsets.UTF_8.toString())
