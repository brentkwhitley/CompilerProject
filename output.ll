(DATA  a)
(FUNCTION  addThem  [(int d) (int e)]
  (BB 2
    (OPER 3 Func_Entry []  [])
  )
  (BB 3
    (OPER 4 Add_I [(r 4)]  [(r 1)(r 2)])
    (OPER 5 Mov [(r 3)]  [(r 4)])
    (OPER 6 Mov [(m RetReg)]  [(r 3)])
    (OPER 7 Jmp []  [(bb 1)])
  )
  (BB 1
    (OPER 1 Func_Exit []  [])
    (OPER 2 Return []  [(m RetReg)])
  )
)
(FUNCTION  putDigit  [(int s)]
  (BB 2
    (OPER 3 Func_Entry []  [])
  )
  (BB 3
    (OPER 4 Mov [(r 2)]  [(i 48)])
    (OPER 5 Add_I [(r 3)]  [(r 2)(r 1)])
    (OPER 6 Pass []  [(r 3)] [(PARAM_NUM 0)])
    (OPER 7 JSR []  [(s putchar)] [(numParams 1)])
    (OPER 8 Mov [(r 4)]  [(m RetReg)])
  )
  (BB 1
    (OPER 1 Func_Exit []  [])
    (OPER 2 Return []  [(m RetReg)])
  )
)
(FUNCTION  printInt  [(int r)]
  (BB 2
    (OPER 3 Func_Entry []  [])
  )
  (BB 3
    (OPER 4 Mov [(r 4)]  [(i 0)])
    (OPER 5 Mov [(r 3)]  [(r 4)])
    (OPER 6 Mov [(r 5)]  [(i 10000)])
    (OPER 7 GTE [(r 6)]  [(r 1)(r 5)])
    (OPER 8 BEQ []  [(r 6)(i 0)(bb 5)])
  )
  (BB 4
    (OPER 9 Mov [(r 7)]  [(i 45)])
    (OPER 10 Pass []  [(r 7)] [(PARAM_NUM 0)])
    (OPER 11 JSR []  [(s putchar)] [(numParams 1)])
    (OPER 12 Mov [(r 8)]  [(m RetReg)])
    (OPER 13 Mov [(r 9)]  [(i 1)])
    (OPER 14 Pass []  [(r 9)] [(PARAM_NUM 0)])
    (OPER 15 JSR []  [(s putDigit)] [(numParams 1)])
    (OPER 16 Mov [(r 10)]  [(m RetReg)])
    (OPER 17 Jmp []  [(bb 1)])
  )
  (BB 6
  )
  (BB 1
    (OPER 1 Func_Exit []  [])
    (OPER 2 Return []  [(m RetReg)])
  )
  (BB 10
    (OPER 48 Mov [(r 29)]  [(i 1)])
    (OPER 49 EQ [(r 30)]  [(r 3)(r 29)])
    (OPER 50 BEQ []  [(r 30)(i 0)(bb 13)])
  )
  (BB 12
    (OPER 51 Mov [(r 31)]  [(i 0)])
    (OPER 52 Pass []  [(r 31)] [(PARAM_NUM 0)])
    (OPER 53 JSR []  [(s putDigit)] [(numParams 1)])
    (OPER 54 Mov [(r 32)]  [(m RetReg)])
  )
  (BB 13
    (OPER 55 Jmp []  [(bb 11)])
  )
  (BB 15
    (OPER 69 Mov [(r 41)]  [(i 1)])
    (OPER 70 EQ [(r 42)]  [(r 3)(r 41)])
    (OPER 71 BEQ []  [(r 42)(i 0)(bb 18)])
  )
  (BB 17
    (OPER 72 Mov [(r 43)]  [(i 0)])
    (OPER 73 Pass []  [(r 43)] [(PARAM_NUM 0)])
    (OPER 74 JSR []  [(s putDigit)] [(numParams 1)])
    (OPER 75 Mov [(r 44)]  [(m RetReg)])
  )
  (BB 18
    (OPER 76 Jmp []  [(bb 16)])
  )
  (BB 5
    (OPER 18 Mov [(r 11)]  [(i 1000)])
    (OPER 19 GTE [(r 12)]  [(r 1)(r 11)])
    (OPER 20 BEQ []  [(r 12)(i 0)(bb 8)])
  )
  (BB 7
    (OPER 21 Mov [(r 13)]  [(i 1000)])
    (OPER 22 Div_I [(r 14)]  [(r 1)(r 13)])
    (OPER 23 Mov [(r 2)]  [(r 14)])
    (OPER 24 Pass []  [(r 2)] [(PARAM_NUM 0)])
    (OPER 25 JSR []  [(s putDigit)] [(numParams 1)])
    (OPER 26 Mov [(r 15)]  [(m RetReg)])
    (OPER 27 Mov [(r 16)]  [(i 1000)])
    (OPER 28 Mul_I [(r 17)]  [(r 2)(r 16)])
    (OPER 29 Sub_I [(r 18)]  [(r 1)(r 17)])
    (OPER 30 Mov [(r 1)]  [(r 18)])
    (OPER 31 Mov [(r 19)]  [(i 1)])
    (OPER 32 Mov [(r 3)]  [(r 19)])
  )
  (BB 8
    (OPER 33 Mov [(r 20)]  [(i 100)])
    (OPER 34 GTE [(r 21)]  [(r 1)(r 20)])
    (OPER 35 BEQ []  [(r 21)(i 0)(bb 10)])
  )
  (BB 9
    (OPER 36 Mov [(r 22)]  [(i 100)])
    (OPER 37 Div_I [(r 23)]  [(r 1)(r 22)])
    (OPER 38 Mov [(r 2)]  [(r 23)])
    (OPER 39 Pass []  [(r 2)] [(PARAM_NUM 0)])
    (OPER 40 JSR []  [(s putDigit)] [(numParams 1)])
    (OPER 41 Mov [(r 24)]  [(m RetReg)])
    (OPER 42 Mov [(r 25)]  [(i 100)])
    (OPER 43 Mul_I [(r 26)]  [(r 2)(r 25)])
    (OPER 44 Sub_I [(r 27)]  [(r 1)(r 26)])
    (OPER 45 Mov [(r 1)]  [(r 27)])
    (OPER 46 Mov [(r 28)]  [(i 1)])
    (OPER 47 Mov [(r 3)]  [(r 28)])
  )
  (BB 11
    (OPER 56 Mov [(r 33)]  [(i 10)])
    (OPER 57 GTE [(r 34)]  [(r 1)(r 33)])
    (OPER 58 BEQ []  [(r 34)(i 0)(bb 15)])
  )
  (BB 14
    (OPER 59 Mov [(r 35)]  [(i 10)])
    (OPER 60 Div_I [(r 36)]  [(r 1)(r 35)])
    (OPER 61 Mov [(r 2)]  [(r 36)])
    (OPER 62 Pass []  [(r 2)] [(PARAM_NUM 0)])
    (OPER 63 JSR []  [(s putDigit)] [(numParams 1)])
    (OPER 64 Mov [(r 37)]  [(m RetReg)])
    (OPER 65 Mov [(r 38)]  [(i 10)])
    (OPER 66 Mul_I [(r 39)]  [(r 2)(r 38)])
    (OPER 67 Sub_I [(r 40)]  [(r 1)(r 39)])
    (OPER 68 Mov [(r 1)]  [(r 40)])
  )
  (BB 16
    (OPER 77 Pass []  [(r 1)] [(PARAM_NUM 0)])
    (OPER 78 JSR []  [(s putDigit)] [(numParams 1)])
    (OPER 79 Mov [(r 45)]  [(m RetReg)])
    (OPER 80 Jmp []  [(bb 6)])
  )
)
(FUNCTION  main  []
  (BB 2
    (OPER 3 Func_Entry []  [])
  )
  (BB 3
    (OPER 4 Mov [(r 6)]  [(i 5)])
    (OPER 5 Mov [(r 2)]  [(r 6)])
    (OPER 6 Mov [(r 1)]  [(r 2)])
    (OPER 7 Mov [(r 7)]  [(i 5)])
    (OPER 8 EQ [(r 8)]  [(r 1)(r 7)])
    (OPER 9 BEQ []  [(r 8)(i 0)(bb 5)])
  )
  (BB 4
    (OPER 10 Mov [(r 9)]  [(i 3)])
    (OPER 11 Store []  [(r 9)(s a)])
  )
  (BB 6
    (OPER 15 Mov [(r 11)]  [(i 0)])
    (OPER 16 Mov [(r 3)]  [(r 11)])
    (OPER 17 Mov [(r 12)]  [(i 1)])
    (OPER 18 Mov [(r 5)]  [(r 12)])
    (OPER 19 Mov [(r 13)]  [(i 8)])
    (OPER 20 LTE [(r 14)]  [(r 5)(r 13)])
    (OPER 21 BEQ []  [(r 14)(i 0)(bb 8)])
  )
  (BB 7
    (OPER 22 Add_I [(r 15)]  [(r 3)(r 5)])
    (OPER 23 Mov [(r 3)]  [(r 15)])
    (OPER 24 Mov [(r 16)]  [(i 1)])
    (OPER 25 Add_I [(r 17)]  [(r 5)(r 16)])
    (OPER 26 Mov [(r 5)]  [(r 17)])
    (OPER 27 Mov [(r 18)]  [(i 8)])
    (OPER 28 LTE [(r 19)]  [(r 5)(r 18)])
    (OPER 29 BNE []  [(r 19)(i 0)(bb 7)])
  )
  (BB 8
    (OPER 30 Mov [(r 20)]  [(i 3)])
    (OPER 31 Div_I [(r 21)]  [(r 3)(r 20)])
    (OPER 32 Mov [(r 4)]  [(r 21)])
    (OPER 33 Mov [(r 22)]  [(i 4)])
    (OPER 34 Mul_I [(r 23)]  [(r 4)(r 22)])
    (OPER 35 Mov [(r 3)]  [(r 23)])
    (OPER 36 Load [(r 24)]  [(s a)])
    (OPER 37 Pass []  [(r 24)] [(PARAM_NUM 0)])
    (OPER 38 Pass []  [(r 1)] [(PARAM_NUM 1)])
    (OPER 39 JSR []  [(s addThem)] [(numParams 2)])
    (OPER 40 Mov [(r 25)]  [(m RetReg)])
    (OPER 41 Mov [(r 2)]  [(r 25)])
    (OPER 42 Mov [(r 26)]  [(i 56)])
    (OPER 43 Pass []  [(r 26)] [(PARAM_NUM 0)])
    (OPER 44 JSR []  [(s putchar)] [(numParams 1)])
    (OPER 45 Mov [(r 27)]  [(m RetReg)])
    (OPER 46 Mov [(r 28)]  [(i 61)])
    (OPER 47 Pass []  [(r 28)] [(PARAM_NUM 0)])
    (OPER 48 JSR []  [(s putchar)] [(numParams 1)])
    (OPER 49 Mov [(r 29)]  [(m RetReg)])
    (OPER 50 Add_I [(r 30)]  [(r 2)(r 3)])
    (OPER 51 Pass []  [(r 30)] [(PARAM_NUM 0)])
    (OPER 52 JSR []  [(s putchar)] [(numParams 1)])
    (OPER 53 Mov [(r 31)]  [(m RetReg)])
    (OPER 54 Mov [(r 32)]  [(i 10)])
    (OPER 55 Pass []  [(r 32)] [(PARAM_NUM 0)])
    (OPER 56 JSR []  [(s putchar)] [(numParams 1)])
    (OPER 57 Mov [(r 33)]  [(m RetReg)])
    (OPER 58 Mov [(r 34)]  [(i 0)])
    (OPER 59 Mov [(r 5)]  [(r 34)])
    (OPER 60 Mov [(r 35)]  [(i 10)])
    (OPER 61 LT [(r 36)]  [(r 5)(r 35)])
    (OPER 62 BEQ []  [(r 36)(i 0)(bb 10)])
  )
  (BB 9
    (OPER 63 Mov [(r 37)]  [(i 48)])
    (OPER 64 Add_I [(r 38)]  [(r 37)(r 5)])
    (OPER 65 Pass []  [(r 38)] [(PARAM_NUM 0)])
    (OPER 66 JSR []  [(s putchar)] [(numParams 1)])
    (OPER 67 Mov [(r 39)]  [(m RetReg)])
    (OPER 68 Mov [(r 40)]  [(i 1)])
    (OPER 69 Add_I [(r 41)]  [(r 5)(r 40)])
    (OPER 70 Mov [(r 5)]  [(r 41)])
    (OPER 71 Mov [(r 42)]  [(i 10)])
    (OPER 72 LT [(r 43)]  [(r 5)(r 42)])
    (OPER 73 BNE []  [(r 43)(i 0)(bb 9)])
  )
  (BB 10
    (OPER 74 Mov [(r 44)]  [(i 10)])
    (OPER 75 Pass []  [(r 44)] [(PARAM_NUM 0)])
    (OPER 76 JSR []  [(s putchar)] [(numParams 1)])
    (OPER 77 Mov [(r 45)]  [(m RetReg)])
    (OPER 78 Mov [(r 46)]  [(i 67)])
    (OPER 79 Pass []  [(r 46)] [(PARAM_NUM 0)])
    (OPER 80 JSR []  [(s putchar)] [(numParams 1)])
    (OPER 81 Mov [(r 47)]  [(m RetReg)])
    (OPER 82 Mov [(r 48)]  [(i 83)])
    (OPER 83 Pass []  [(r 48)] [(PARAM_NUM 0)])
    (OPER 84 JSR []  [(s putchar)] [(numParams 1)])
    (OPER 85 Mov [(r 49)]  [(m RetReg)])
    (OPER 86 Mov [(r 50)]  [(i 3510)])
    (OPER 87 Pass []  [(r 50)] [(PARAM_NUM 0)])
    (OPER 88 JSR []  [(s printInt)] [(numParams 1)])
    (OPER 89 Mov [(r 51)]  [(m RetReg)])
    (OPER 90 Mov [(r 52)]  [(i 10)])
    (OPER 91 Pass []  [(r 52)] [(PARAM_NUM 0)])
    (OPER 92 JSR []  [(s putchar)] [(numParams 1)])
    (OPER 93 Mov [(r 53)]  [(m RetReg)])
    (OPER 94 Mov [(r 54)]  [(i 0)])
    (OPER 95 Mov [(r 1)]  [(r 54)])
    (OPER 96 Mov [(r 55)]  [(i 1)])
    (OPER 97 Mov [(r 2)]  [(r 55)])
    (OPER 98 Mov [(r 56)]  [(i 1)])
    (OPER 99 Mov [(r 3)]  [(r 56)])
    (OPER 100 Mov [(r 57)]  [(i 0)])
    (OPER 101 Mov [(r 4)]  [(r 57)])
    (OPER 102 Mov [(r 58)]  [(i 0)])
    (OPER 103 Mov [(r 5)]  [(r 58)])
    (OPER 104 Mov [(r 59)]  [(i 0)])
    (OPER 105 EQ [(r 60)]  [(r 1)(r 59)])
    (OPER 106 BEQ []  [(r 60)(i 0)(bb 12)])
  )
  (BB 11
    (OPER 107 Mov [(r 61)]  [(i 0)])
    (OPER 108 EQ [(r 62)]  [(r 2)(r 61)])
    (OPER 109 BEQ []  [(r 62)(i 0)(bb 15)])
  )
  (BB 14
    (OPER 110 Mov [(r 63)]  [(i 1)])
    (OPER 111 Mov [(r 5)]  [(r 63)])
  )
  (BB 16
  )
  (BB 13
    (OPER 130 Mov [(r 72)]  [(i 10)])
    (OPER 131 EQ [(r 73)]  [(r 5)(r 72)])
    (OPER 132 BEQ []  [(r 73)(i 0)(bb 24)])
  )
  (BB 23
    (OPER 133 Mov [(r 74)]  [(i 99)])
    (OPER 134 Pass []  [(r 74)] [(PARAM_NUM 0)])
    (OPER 135 JSR []  [(s putchar)] [(numParams 1)])
    (OPER 136 Mov [(r 75)]  [(m RetReg)])
    (OPER 137 Mov [(r 76)]  [(i 0)])
    (OPER 138 Pass []  [(r 76)] [(PARAM_NUM 0)])
    (OPER 139 JSR []  [(s putDigit)] [(numParams 1)])
    (OPER 140 Mov [(r 77)]  [(m RetReg)])
    (OPER 141 Mov [(r 78)]  [(i 0)])
    (OPER 142 Pass []  [(r 78)] [(PARAM_NUM 0)])
    (OPER 143 JSR []  [(s putDigit)] [(numParams 1)])
    (OPER 144 Mov [(r 79)]  [(m RetReg)])
    (OPER 145 Mov [(r 80)]  [(i 108)])
    (OPER 146 Pass []  [(r 80)] [(PARAM_NUM 0)])
    (OPER 147 JSR []  [(s putchar)] [(numParams 1)])
    (OPER 148 Mov [(r 81)]  [(m RetReg)])
  )
  (BB 25
    (OPER 169 Mov [(r 91)]  [(i 10)])
    (OPER 170 Pass []  [(r 91)] [(PARAM_NUM 0)])
    (OPER 171 JSR []  [(s putchar)] [(numParams 1)])
    (OPER 172 Mov [(r 92)]  [(m RetReg)])
    (OPER 173 Mov [(r 93)]  [(i 0)])
    (OPER 174 Mov [(m RetReg)]  [(r 93)])
    (OPER 175 Jmp []  [(bb 1)])
  )
  (BB 1
    (OPER 1 Func_Exit []  [])
    (OPER 2 Return []  [(m RetReg)])
  )
  (BB 5
    (OPER 12 Mov [(r 10)]  [(i 4)])
    (OPER 13 Store []  [(r 10)(s a)])
    (OPER 14 Jmp []  [(bb 6)])
  )
  (BB 21
    (OPER 122 Mov [(r 70)]  [(i 3)])
    (OPER 123 Mov [(r 5)]  [(r 70)])
    (OPER 124 Jmp []  [(bb 22)])
  )
  (BB 18
    (OPER 117 Mov [(r 67)]  [(i 0)])
    (OPER 118 EQ [(r 68)]  [(r 4)(r 67)])
    (OPER 119 BEQ []  [(r 68)(i 0)(bb 21)])
  )
  (BB 20
    (OPER 120 Mov [(r 69)]  [(i 10)])
    (OPER 121 Mov [(r 5)]  [(r 69)])
  )
  (BB 22
    (OPER 125 Jmp []  [(bb 19)])
  )
  (BB 15
    (OPER 112 Mov [(r 64)]  [(i 0)])
    (OPER 113 EQ [(r 65)]  [(r 3)(r 64)])
    (OPER 114 BEQ []  [(r 65)(i 0)(bb 18)])
  )
  (BB 17
    (OPER 115 Mov [(r 66)]  [(i 2)])
    (OPER 116 Mov [(r 5)]  [(r 66)])
  )
  (BB 19
    (OPER 126 Jmp []  [(bb 16)])
  )
  (BB 12
    (OPER 127 Mov [(r 71)]  [(i 0)])
    (OPER 128 Mov [(r 5)]  [(r 71)])
    (OPER 129 Jmp []  [(bb 13)])
  )
  (BB 24
    (OPER 149 Mov [(r 82)]  [(i 98)])
    (OPER 150 Pass []  [(r 82)] [(PARAM_NUM 0)])
    (OPER 151 JSR []  [(s putchar)] [(numParams 1)])
    (OPER 152 Mov [(r 83)]  [(m RetReg)])
    (OPER 153 Mov [(r 84)]  [(i 97)])
    (OPER 154 Pass []  [(r 84)] [(PARAM_NUM 0)])
    (OPER 155 JSR []  [(s putchar)] [(numParams 1)])
    (OPER 156 Mov [(r 85)]  [(m RetReg)])
    (OPER 157 Mov [(r 86)]  [(i 100)])
    (OPER 158 Pass []  [(r 86)] [(PARAM_NUM 0)])
    (OPER 159 JSR []  [(s putchar)] [(numParams 1)])
    (OPER 160 Mov [(r 87)]  [(m RetReg)])
    (OPER 161 Mov [(r 88)]  [(i 61)])
    (OPER 162 Pass []  [(r 88)] [(PARAM_NUM 0)])
    (OPER 163 JSR []  [(s putchar)] [(numParams 1)])
    (OPER 164 Mov [(r 89)]  [(m RetReg)])
    (OPER 165 Pass []  [(r 5)] [(PARAM_NUM 0)])
    (OPER 166 JSR []  [(s printInt)] [(numParams 1)])
    (OPER 167 Mov [(r 90)]  [(m RetReg)])
    (OPER 168 Jmp []  [(bb 25)])
  )
)
