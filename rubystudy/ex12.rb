print "Give me a number: "
number = gets.chomp.to_i
bigger = number * 100
puts "A bigger number is #{bigger}."
print "Give me another number: "
another = gets.chomp
number = another.to_i
smaller = number / 100
number1 = 103.4
number2 = number1 / 10
number3 = number2.to_i
puts "A smaller number is #{smaller}."
puts "number3 : #{number3}"