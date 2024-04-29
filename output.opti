(DATA  x)
(DATA  y)
(DATA  z)
(FUNCTION  main  []
  (BB 2
    (OPER 3 Func_Entry []  [])
  )
  (BB 3
    (OPER 5 Mov [(r 2)]  [(i 4)])
    (OPER 4 Mov [(r 1)]  [(r 2)])
    (OPER 6 Mov [(r 3)]  [(i 4)])
    (OPER 7 EQ [(r 4)]  [(r 1)(r 3)])
    (OPER 8 BEQ []  [(r 4)(r 0)(bb 5)])
  )
  (BB 4
    (OPER 9 Mov [(r 5)]  [(i 48)])
    (OPER 10 Pass []  [(r 5)] [(PARAM_NUM 0)])
    (OPER 11 JSR []  [(s putchar)] [(numParams 1)])
    (OPER 12 Mov [(r 6)]  [(m RetReg)])
  )
  (BB 6
    (OPER 18 Mov [(r 9)]  [(i 55)])
    (OPER 19 Pass []  [(r 9)] [(PARAM_NUM 0)])
    (OPER 20 JSR []  [(s putchar)] [(numParams 1)])
    (OPER 21 Mov [(r 10)]  [(m RetReg)])
  )
  (BB 1
    (OPER 1 Func_Exit []  [])
    (OPER 2 Return []  [(m RetReg)])
  )
  (BB 5
    (OPER 13 Mov [(r 7)]  [(i 50)])
    (OPER 14 Pass []  [(r 7)] [(PARAM_NUM 0)])
    (OPER 15 JSR []  [(s putchar)] [(numParams 1)])
    (OPER 16 Mov [(r 8)]  [(m RetReg)])
    (OPER 17 Jmp []  [(bb 6)])
  )
)
