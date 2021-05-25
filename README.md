# IS MUTANT SERVICE
Service for MELI test
# Author
### :bowtie: [Juan Kamacho](https://kmacho16.github.io/) 

### Deploy gradle
    /.gradlew clean build
    /.gradlew clean run

## Use 
####Base URL
`https://meli-test-kmacho.herokuapp.com/api`

``` 
[POST] /mutant
{
    "dna": [
        "ATGCGA",
        "CAGTGC",
        "TTATGT",
        "AGAAGG",
        "TCCCTA",
        "TCACTG"
    ]
}
```

``` 
[GET] /stats
{
    "count_human_dna": "1",
    "count_mutant_dna": "2",
    "ratio": "0.6666666666666666"
}
```

### Rules
Only will be count diferent strings 
```
    A   T   G   C   G   A
    C   A   G   T   G   C
    T   T   A   T   G   T
    A   G   A   A   G   G
    C   C   C   C   T   A
    T   C   A   C   T   G
    
    AAAA GGGG CCCC => [OK]
```
    
```
    A   T   G   C   A   A
    C   A   G   T   A   C
    T   T   A   T   A   T
    A   G   A   A   A   G
    T   C   C   C   T   A
    T   C   A   C   T   G
    
    AAAA GGGG AAAA = [BAD]
```

### Database  Example

| id | cadena_hash                      | cadena_dna                                       | cadena_mutante     | is_mutant |
|----|----------------------------------|--------------------------------------------------|--------------------|-----------|
| 4  | 0a83414c6aa6c2fde0717441581779d2 | [ATGCGA, CAGTGC, TTATGT, AGAAGG, CCCCTA, TCACTG] | [GGGG, AAAA, CCCC] | T         |
| 5  | f5fe8ca63a04ae23c15626409f559113 | [BTGCBA, CAGTGC, TTATGT, AGAAGG, CCCCTA, TCACTG] | [CCCC]             | F         |