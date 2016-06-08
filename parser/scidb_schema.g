grammar scidb_schema;

identifier
	: ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'0'..'9'|'_')*
	;

integer 
	: '0'..'9'+
	;

schema
 	: '<' attributes '>' '[' dimensions ']'
	;
	
attributes
	: attribute
	| attribute ',' attributes
	;

attribute
	: identifier ':' identifier nullable compression
	;

nullable
	: 'NULL'
	| 'NOT NULL'
	| blank
	;

compression
	: COMPRESSION constant_string
	| blank
	;
	
dimensions
	: dimension
	| dimension dimsep dimensions
	;

dimsep
	: ','
	| ';'
	;

dimension
	: identifier '=' dexp ':' dhigh ',' dhigh ',' dexp
	| identifier
	;

dhigh
	: dexp
	| '*'
	;

dexp
	: integer
	| '?'
	;
	
blank
	:
	;

