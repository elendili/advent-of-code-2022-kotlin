fun getResourceAsLines(path: String): List<String> =
    object {}.javaClass.getResource(path).readText().lines()
