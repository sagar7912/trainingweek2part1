
rootProject.name = "Fretron-Week2-Assignment1"
include("src:main:Service")
findProject(":src:main:Service")?.name = "Service"
include("src:main:untitled")
findProject(":src:main:untitled")?.name = "untitled"
