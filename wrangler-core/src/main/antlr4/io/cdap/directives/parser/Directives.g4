lexer grammar Directives;

BYTE_SIZE: [0-9]+ ('.' [0-9]+)? BYTE_UNIT;
TIME_DURATION: [0-9]+ ('.' [0-9]+)? TIME_UNIT;

fragment BYTE_UNIT: [Kk][Bb] | [Mm][Bb] | [Gg][Bb] | [Tt][Bb] | [Bb];
fragment TIME_UNIT: 'ms' | 's' | 'sec' | 'm' | 'min' | 'h';
