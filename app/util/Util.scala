package util

import java.util.Date

object Util {
	def generateName(): String = {
		new Date().getTime().toString() + ".txt"
	}
}