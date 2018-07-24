# Writes string text from PG to a file, one file per row

The input format should be a CSV file with:

```
filename,text
foo, """# \n# See ""Later"" for more info"""
```

...and writes out a file for each row (e.g., `foo.txt`) with the text expanded (e.g., \n becomes a new line).

Run with:

```
sbt> run file.csv
```

From Postgres you could perhaps create such a CSV file with:

```sql
\copy (
   select
     school || '_' ||  key as "filename", 
     value as "text"
  from 
     school_config order by school
  ) to '/tmp/all-settings.csv' csv header;
```





