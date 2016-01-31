i = 0
numbers = []


def no_name (i, numbers)
	 puts "At the top i is #{i}"
	 numbers.push(i)
	puts "Numbers now: ", numbers
  puts "At the bottom i is #{i}"
end
no_name(1, numbers)
no_name(2, numbers)
no_name(3, numbers)
no_name(4, numbers)
no_name(5, numbers)	


puts "The numbers: "
numbers.each {|num| puts num }